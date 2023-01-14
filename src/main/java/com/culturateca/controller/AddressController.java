package com.culturateca.controller;

import com.culturateca.controller.dto.AddressDto;
import com.culturateca.model.Address;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/address")
public class AddressController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveAddress(@Valid @RequestBody AddressDto address){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewAddress(address.toAddress()).getAddressId();
    }

    @GetMapping("/{addressId}")
    public AddressDto findAddressById(@RequestParam("addressId") Long addressId){
        //todo implement checks and validations for formatting and data type
        return AddressDto.toAddressDto(culturatecaService.findAddressById(addressId));
    }

    @GetMapping()
    public List<AddressDto> findAllAddresses(){
        //todo implement checks and validations for formatting and data type
        List<Address> addresses = culturatecaService.findAllAddresses();
        List<AddressDto> addressDtos = new ArrayList<AddressDto>();
        for (Address address:addresses) {
            addressDtos.add(AddressDto.toAddressDto(address));
        }
        return addressDtos;
    }

    @DeleteMapping("/{addressId}")
    public void deleteAddressById(@RequestParam("addressId") Long addressId){
        culturatecaService.deleteAddressById(addressId);
    }

    @PatchMapping()
    public AddressDto updateAddress(@Valid @RequestBody Address address){
        //todo implement checks and validations for formatting and data type
        return AddressDto.toAddressDto(culturatecaService.updateAddress(address));
    }

    @PutMapping("/{addressId}/locations{locationsId}/publisher{publisherId}/studio{studioId}")
    public AddressDto updateAddressRelations(@RequestParam(value = "addressId", required = true) Long addressId,
                                          @RequestParam(value = "locationsId", required = false) List<Long> locationIds,
                                          @RequestParam(value = "publisherId", required = false) Long publisherId,
                                          @RequestParam(value = "studioId", required = false) Long studioId
    ){
        return AddressDto.toAddressDto(culturatecaService.updateAddressRelations(addressId,locationIds,publisherId,studioId));
    }
}
