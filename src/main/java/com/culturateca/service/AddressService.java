package com.culturateca.service;

import com.culturateca.model.Address;

public interface AddressService {

    Long saveNewAddress(Address address);

    Address findAddressById(Long id);
}
