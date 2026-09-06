package com.social.culturateca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.Secutiry.JwtService;
import com.social.culturateca.controller.DTO.LoginRequestDTO;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired 
    JwtService jwtService;

    @PostMapping
    public String login(@RequestBody LoginRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email,
                request.password)
        );
        UserDetails user = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(user);
    }
}
