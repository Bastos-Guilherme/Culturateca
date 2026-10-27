package com.social.culturateca.service;

import com.social.culturateca.controller.DTO.ItemRegistrationRequest;
import com.social.culturateca.model.Copy;

public interface ItemRegistrationService {

    Copy registerItem(
        ItemRegistrationRequest request,
        String curatorEmail
    );
}