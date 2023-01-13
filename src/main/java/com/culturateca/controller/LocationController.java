package com.culturateca.controller;

import com.culturateca.controller.dto.LocationDto;
import com.culturateca.model.Location;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/location/")
public class LocationController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveLocation(@Valid @RequestBody LocationDto location){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewLocation(location.toLocation()).getLocationId();
    }

    @GetMapping("/getById/{locationId}")
    public Location findLocationById(@RequestParam Long locationId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findLocationById(locationId);
    }

    @GetMapping("/getAll")
    public List<Location> findAllLocationes(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllLocations();
    }

    @DeleteMapping("/delete/{locationId}")
    public void deleteLocationById(@RequestParam Long locationId){
        culturatecaService.deleteLocationById(locationId);
    }

    @PutMapping("/update")
    public Location updateLocation(@Valid @RequestBody Location location){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateLocation(location);
    }
}