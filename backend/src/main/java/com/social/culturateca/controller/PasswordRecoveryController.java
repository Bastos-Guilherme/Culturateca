
package com.social.culturateca.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.service.PasswordRecoveryService;

@RestController
@RequestMapping("/password-recovery")
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    public PasswordRecoveryController(PasswordRecoveryService passwordRecoveryService) {
        this.passwordRecoveryService = passwordRecoveryService;
    }

    @PostMapping("/request-code")
    public ResponseEntity<Map<String, String>> requestCode(@RequestBody RequestCodeDTO request) {

        String challengeId = passwordRecoveryService.requestCode(request.email());

        return ResponseEntity.ok(Map.of(
                "message",
                "Se o e-mail estiver cadastrado, você receberá "
                        + "um código de recuperação.",
                "challengeId",
                challengeId));
    }

    @PostMapping("/verify-code")
    public ResponseEntity<Map<String, String>> verifyCode(@RequestBody VerifyCodeDTO request) {
        passwordRecoveryService.verifyCode(
                request.challengeId(), request.code());

        return ResponseEntity.ok(Map.of(
                "message", "Código validado com sucesso."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordDTO request) {
        passwordRecoveryService.resetPassword(
                request.challengeId(),
                request.code(),
                request.newPassword());

        return ResponseEntity.ok(Map.of(
                "message", "Senha redefinida com sucesso."));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException exception) {

        return ResponseEntity.badRequest().body(Map.of(
                "message", exception.getMessage()));
    }

    public record RequestCodeDTO(String email) {
    }

    public record VerifyCodeDTO(String challengeId, String code) {
    }

    public record ResetPasswordDTO(
            String challengeId,
            String code,
            String newPassword) {
    }
}
