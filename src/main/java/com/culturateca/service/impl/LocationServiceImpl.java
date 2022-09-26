package com.culturateca.service.impl;

import com.culturateca.model.Location;
import com.culturateca.model.repository.LocationRepository;
import com.culturateca.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location findLocationById(Long id){
        return locationRepository.findById(id).get();
    }
}
