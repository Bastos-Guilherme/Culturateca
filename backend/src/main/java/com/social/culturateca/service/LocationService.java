package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Location;

public interface LocationService {
    
    public List<Location> locationAll();

    public Location findById(Long id);

    public List<Location> findByName(String name);

    public Location createLocation(Location location);

    public Location editLocation(Location location);
    
    public void deleteLocation(Long id);

}
