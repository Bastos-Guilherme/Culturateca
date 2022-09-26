package com.culturateca.service.impl;

import com.culturateca.model.Address;
import com.culturateca.model.repository.AddressRepository;
import com.culturateca.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Long saveNewAddress(Address address){
        return addressRepository.save(address).getAddressId();
    };

    @Override
    public Address findAddressById(Long id){
        return addressRepository.findById(id).get();
    }
}
