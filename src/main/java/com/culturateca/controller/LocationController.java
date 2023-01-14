package com.culturateca.controller;

import com.culturateca.controller.dto.LocationDto;
import com.culturateca.model.Location;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/location")
public class LocationController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveLocation(@Valid @RequestBody LocationDto location){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewLocation(location.toLocation()).getLocationId();
    }

    @GetMapping("/{locationId}")
    public LocationDto findLocationById(@RequestParam Long locationId){
        //todo implement checks and validations for formatting and data type
        return LocationDto.toLocationDto(culturatecaService.findLocationById(locationId));
    }

    @GetMapping()
    public List<LocationDto> findAllLocations(){
        //todo implement checks and validations for formatting and data type
        List<Location> locations = culturatecaService.findAllLocations();
        List<LocationDto> locationDtos = new ArrayList<LocationDto>();
        for (Location location:locations) {
            locationDtos.add(LocationDto.toLocationDto(location));
        }
        return locationDtos;
    }

    @DeleteMapping("/{locationId}")
    public void deleteLocationById(@RequestParam Long locationId){
        culturatecaService.deleteLocationById(locationId);
    }

    @PatchMapping()
    public LocationDto updateLocation(@Valid @RequestBody Location location){
        //todo implement checks and validations for formatting and data type
        return LocationDto.toLocationDto(culturatecaService.updateLocation(location));
    }

    @PutMapping("/{locationId}/masterpieces{masterPieceIds}/address{addressId}")
    public LocationDto updateLocationRelations(@RequestParam(value = "locationId", required = true) Long locationId,
                                            @RequestParam(value = "addressId", required = false) Long addressId,
                                            @RequestParam(value = "masterPieceIds", required = false) List<Long> masterPieceIds
    ){
        return LocationDto.toLocationDto(culturatecaService.updateLocationRelations(locationId,addressId,masterPieceIds));
    }
}