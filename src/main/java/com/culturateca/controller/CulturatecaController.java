package com.culturateca.controller;

import com.culturateca.controller.dto.AddressDto;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/masterpieceapi/")
public class CulturatecaController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/saveNewAddress")
    @Headers("Content-Type: application/json")
    public Long saveNewAddress(@Valid @RequestBody AddressDto address){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewAddress(address.toAddress()).getAddressId();
    }
}
