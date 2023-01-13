package com.culturateca.service.impl;

import com.culturateca.model.Location;
import com.culturateca.model.repository.LocationRepository;
import com.culturateca.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Location save(Location location){
        return locationRepository.save(location);
    };

    @Override
    public Location findById(Long id){
        return locationRepository.findById(id).get();
    }

    @Override
    public List<Location> findAll(){ return locationRepository.findAll(); }

    @Override
    public void deleteById(long id){ locationRepository.deleteById(id); }
}