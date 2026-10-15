
package com.social.culturateca.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.controller.DTO.RegisterRequestDTO;
import com.social.culturateca.controller.DTO.VerifyRegistrationDTO;
import com.social.culturateca.service.RegistrationVerificationService;
import com.social.culturateca.service.implementation.RegistrationException;

@RestController
@RequestMapping("/curator/register")
public class RegistrationVerificationController {

    private final RegistrationVerificationService registrationService;

    public RegistrationVerificationController(RegistrationVerificationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/request-code")
    public ResponseEntity<Map<String, String>> requestCode(@RequestBody RegisterRequestDTO request) {
        registrationService.requestCode(request);

        return ResponseEntity.ok(Map.of(
                "message",
                "Se a solicitação for válida, enviaremos o código de verificação."));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Map<String, String>> verifyCode(@RequestBody VerifyRegistrationDTO request) {
        registrationService.verifyCode(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message",
                "E-mail verificado e cadastro concluído com sucesso."));
    }

    @ExceptionHandler({
        IllegalArgumentException.class,
        RegistrationException.class
    })
    public ResponseEntity<Map<String, String>> handleRegistrationError(RuntimeException exception) {
        return ResponseEntity.badRequest().body(Map.of(
                "message",
                exception.getMessage() == null
                        ? "Não foi possível concluir a solicitação."
                        : exception.getMessage()));
    }
}
