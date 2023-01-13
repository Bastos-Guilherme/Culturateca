package com.culturateca.service;

import com.culturateca.model.Location;

import java.util.List;

public interface LocationService {

    Location save(Location location);

    Location findById(Long id);

    List<Location> findAll();

    void deleteById(long id);
}