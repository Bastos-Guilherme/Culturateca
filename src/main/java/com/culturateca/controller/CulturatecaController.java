package com.culturateca.controller;

import com.culturateca.service.CulturatecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/")
public class CulturatecaController {

    @Autowired
    CulturatecaService culturatecaService;
}
