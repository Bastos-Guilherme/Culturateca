package com.social.culturateca.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.controller.DTO.ItemRegistrationRequest;
import com.social.culturateca.model.Copy;
import com.social.culturateca.service.ItemRegistrationService;

@RestController
@RequestMapping("/item-registration")
public class ItemRegistrationController {

    private final ItemRegistrationService itemRegistrationService;

    public ItemRegistrationController(
            ItemRegistrationService itemRegistrationService) {
        this.itemRegistrationService = itemRegistrationService;
    }

    @PostMapping
    public Copy register(
            @RequestBody ItemRegistrationRequest request,
            Authentication authentication) {

        return itemRegistrationService.registerItem(
            request,
            authentication.getName()
        );
    }
}