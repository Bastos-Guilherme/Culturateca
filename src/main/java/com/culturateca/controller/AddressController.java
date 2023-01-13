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
    public Address findAddressById(@RequestParam("addressId") Long addressId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAddressById(addressId);
    }

    @GetMapping("/getAll")
    public List<Address> findAllAddresses(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllAddresses();
    }

    @DeleteMapping("/delete/{addressId}")
    public void deleteAddressById(@RequestParam("addressId") Long addressId){
        culturatecaService.deleteAddressById(addressId);
    }

    @PutMapping("/update")
    public Address updateAddress(@Valid @RequestBody Address address){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateAddress(address);
    }

    @PutMapping("/add/into{addressId}/locations{locationsId}/publisher{publisherId}/studio{studioId}")
    public Address updateAddressRelations(@RequestParam(value = "addressId", required = true) Long addressId,
                                          @RequestParam(value = "locationsId", required = false) List<Long> locationIds,
                                          @RequestParam(value = "publisherId", required = false) Long publisherId,
                                          @RequestParam(value = "studioId", required = false) Long studioId
    ){
        return culturatecaService.updateAddressRelations(addressId,locationIds,publisherId,studioId);
    }
}
