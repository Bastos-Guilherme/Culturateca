package com.culturateca.controller.dto;

import com.culturateca.model.Collection;
import com.culturateca.model.Location;
import com.culturateca.service.CulturatecaService;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
public class LocationDto {

    private Long locationId;
    private AddressDto address;
    private String room;
    private String unit;
    private String shelf;
    private MasterPieceDto masterpiece;

    public LocationDto(){};

    public LocationDto toLocationDto(Location location){return new LocationDto();}

    public Location toLocation(CulturatecaService culturatecaService) {return new Location();}
}