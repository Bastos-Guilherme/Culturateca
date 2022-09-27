package com.culturateca.controller;

import com.culturateca.controller.dto.AddressDto;
import com.culturateca.service.CulturatecaService;
import com.culturateca.service.LocationService;
import com.culturateca.service.PublisherService;
import com.culturateca.service.StudioService;
import feign.Headers;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/masterpieceapi/")
public class CulturatecaController {

    @Autowired
    CulturatecaService culturatecaService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    StudioService studioService;

    @Autowired
    LocationService locationService;

    @PostMapping("/saveNewAddress")
    @Headers("Content-Type: application/json")
    public Long saveNewAddress(@Valid @RequestBody AddressDto body){
        return culturatecaService.saveNewAddress(body.toAddress(locationService,publisherService,studioService)).getAddressId();
    }
}
