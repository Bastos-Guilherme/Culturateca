package com.culturateca.service.impl;

import com.culturateca.model.Address;
import com.culturateca.model.repository.AddressRepository;
import com.culturateca.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Override
    public Address save(Address address){
        return addressRepository.save(address);
    };

    @Override
    public Address findById(Long id){
        return addressRepository.findById(id).get();
    }

    @Override
    public List<Address> findAll(){ return addressRepository.findAll(); }

    @Override
    public void deleteById(long id){ addressRepository.deleteById(id); }
}