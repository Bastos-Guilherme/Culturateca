
package com.social.culturateca.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.Security.CuratorDetailsService;
import com.social.culturateca.Security.JwtService;
import com.social.culturateca.controller.DTO.LoginRequestDTO;
import com.social.culturateca.controller.DTO.LoginResponseDTO;
import com.social.culturateca.controller.DTO.VerifyLoginDTO;
import com.social.culturateca.model.Curator;
import com.social.culturateca.model.repository.CuratorRepository;
import com.social.culturateca.service.LoginVerificationService;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CuratorRepository curatorRepository;
    private final LoginVerificationService loginVerificationService;
    private final CuratorDetailsService curatorDetailsService;

    public LoginController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            CuratorRepository curatorRepository,
            LoginVerificationService loginVerificationService,
            CuratorDetailsService curatorDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.curatorRepository = curatorRepository;
        this.loginVerificationService = loginVerificationService;
        this.curatorDetailsService = curatorDetailsService;
    }

    @PostMapping
    public Object login(@RequestBody LoginRequestDTO request) {
        String email = request.email().trim().toLowerCase();

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                email,
                request.password()
            )
        );
        Curator curator = curatorRepository.findByEmail(email);

        if (curator == null) { throw new IllegalArgumentException(
            "Não foi possível localizar a conta.");
        }

        if (Boolean.TRUE.equals(curator.getEmailMfaEnabled())) {
            String challengeId = loginVerificationService.requestCode(email);

            return new LoginResponseDTO(
                    true,
                    challengeId,
                    "Código de verificação enviado para seu e-mail."
            );
        }

        UserDetails user = (UserDetails) authentication.getPrincipal();

        return jwtService.generateToken(user);
    }

    @PostMapping("/verify-code")
    public String verifyCode(@RequestBody VerifyLoginDTO request) {
        String email = loginVerificationService.verifyCode(
                request.challengeId(),
                request.code()
        );

        UserDetails user = curatorDetailsService.loadUserByUsername(email);
        return jwtService.generateToken(user);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationError( AuthenticationException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(Map.of(
                "message",
                "E-mail ou senha incorretos."
            ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleValidationError( IllegalArgumentException exception) {
        return ResponseEntity.badRequest()
            .body(Map.of(
                "message",
                exception.getMessage() == null
                        ? "Não foi possível concluir a solicitação."
                        : exception.getMessage()
            ));
    }
}
