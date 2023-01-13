package com.culturateca.service;

import com.culturateca.model.Address;

import java.util.List;

public interface AddressService {

    Address saveNew(Address address);

    Address findById(Long id);

    List<Address> findAll();

    void deleteById(long id);
}