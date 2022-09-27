package com.culturateca.service;

import com.culturateca.model.Address;

public interface AddressService {

    Address saveNewAddress(Address address);

    Address findAddressById(Long id);
}
