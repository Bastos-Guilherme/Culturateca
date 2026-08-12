package com.social.culturateca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.model.Location;
import com.social.culturateca.service.LocationService;

@RestController
@RequestMapping("/location")
public class LocationController {
    
    @Autowired
    LocationService locationService;

    @GetMapping
    public List<Location> getAllLocation(){
        return locationService.locationAll();
    }

    @GetMapping("/id={id}")
    public Location getById(@PathVariable long id){
        return locationService.findById(id);
    }

    @GetMapping("/name={name}")
    public List<Location> getByName(@PathVariable String name){
        return locationService.findByName(name);
    }

    @PostMapping
    public Location create(@RequestBody Location location){
        return locationService.createLocation(location);
    }

    @PutMapping
    public Location edit(@RequestBody Location location){
        return locationService.editLocation(location);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        locationService.deleteLocation(id);
    }
}
