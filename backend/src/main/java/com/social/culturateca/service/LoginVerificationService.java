
package com.social.culturateca.service;

public interface LoginVerificationService {

    String requestCode(String email);

    String verifyCode(String challengeId, String code);
}