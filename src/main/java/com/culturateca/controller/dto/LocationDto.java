package com.culturateca.controller.dto;

import com.culturateca.model.Address;
import com.culturateca.model.Collection;
import com.culturateca.model.Location;
import com.culturateca.model.MasterPiece;
import com.culturateca.service.CulturatecaService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class LocationDto {

    private Long locationId;
    private AddressDto address;
    @NotBlank
    @NotNull
    @NotEmpty
    private String room;
    @NotBlank
    @NotNull
    @NotEmpty
    private String unit;
    @NotBlank
    @NotNull
    @NotEmpty
    private String shelf;
    private MasterPieceDto masterpiece;

    @JsonCreator
    public LocationDto(
            @JsonProperty("locationId") Long locationId,
            @JsonProperty("address") AddressDto address,
            @JsonProperty("room") String room,
            @JsonProperty("unit") String unit,
            @JsonProperty("shelf") String shelf,
            @JsonProperty("masterpiece") MasterPieceDto masterpiece
    ){
        this.setLocationId(locationId);
        this.setAddress(address);
        this.setRoom(room);
        this.setUnit(unit);
        this.setShelf(shelf);
        this.setMasterpiece(masterpiece);
    }

    public static LocationDto toLocationDto(Location location){
        return new LocationDto(
                location.getLocationId(),
                null,
                location.getRoom(),
                location.getUnit(),
                location.getShelf(),
                null
        );
    }

    public Location toLocation(CulturatecaService culturatecaService) {
        Address addressFromDto;
        if (this.getAddress() == null){
            addressFromDto = null;
        } else if (this.getAddress().getAddressId() == null) {
            addressFromDto = this.getAddress().toAddress(culturatecaService);
        } else {
            addressFromDto = culturatecaService.findAddressById(this.getAddress().getAddressId());
        }
        MasterPiece masterPieceFromDto;
        if (this.getMasterpiece() == null){
            masterPieceFromDto = null;
        } else if (this.getMasterpiece().getMasterPieceId() == null) {
            masterPieceFromDto = this.getMasterpiece().toMasterPiece(culturatecaService);
        } else {
            masterPieceFromDto = culturatecaService.findMasterPieceById(this.getMasterpiece().getMasterPieceId());
        }
        return new Location(
                this.getLocationId(),
                addressFromDto,
                this.getRoom(),
                this.getUnit(),
                this.getShelf(),
                masterPieceFromDto
        );
    }
}