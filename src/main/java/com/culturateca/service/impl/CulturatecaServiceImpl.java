package com.culturateca.service.impl;

import com.culturateca.model.Address;
import com.culturateca.service.AddressService;
import com.culturateca.service.CulturatecaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CulturatecaServiceImpl implements CulturatecaService {

    @Autowired
    AddressService addressService;

    @Override
    public Address saveNewAddress(Address address){
        //todo check for uniqueness into address
        return addressService.saveNewAddress(address);
    }
}
