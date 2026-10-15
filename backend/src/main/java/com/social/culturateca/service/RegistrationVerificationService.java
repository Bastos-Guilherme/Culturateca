package com.social.culturateca.service;

import com.social.culturateca.controller.DTO.RegisterRequestDTO;
import com.social.culturateca.controller.DTO.VerifyRegistrationDTO;

public interface RegistrationVerificationService {

    void requestCode(RegisterRequestDTO request);

    void verifyCode(VerifyRegistrationDTO request);
}
