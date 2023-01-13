package com.culturateca.controller;

import com.culturateca.controller.dto.AddressDto;
import com.culturateca.model.Address;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/address/")
public class AddressController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveAddress(@Valid @RequestBody AddressDto address){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewAddress(address.toAddress()).getAddressId();
    }

    @GetMapping("/getById/{addressId}")
    public Address findAddressById(@RequestParam Long addressId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAddressById(addressId);
    }

    @GetMapping("/getAll")
    public List<Address> findAllAddresses(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllAddresses();
    }

    @DeleteMapping("/delete/{addressId}")
    public void deleteAddressById(@RequestParam Long addressId){
        culturatecaService.deleteAddressById(addressId);
    }

    @PutMapping("/update")
    public Address updateAddress(@Valid @RequestBody Address address){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateAddress(address);
    }
}
