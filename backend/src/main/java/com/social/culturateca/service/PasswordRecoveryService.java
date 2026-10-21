package com.social.culturateca.service;

public interface PasswordRecoveryService {

    String requestCode(String email);

    void verifyCode(String challengeId, String code);

    void resetPassword(
            String challengeId,
            String code,
            String newPassword
    );
}

