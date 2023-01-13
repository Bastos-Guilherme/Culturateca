package com.culturateca.controller.dto;

import com.culturateca.model.Location;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class LocationDto {

    private Long locationId;
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

    @JsonCreator
    public LocationDto(
            @JsonProperty("locationId") Long locationId,
            @JsonProperty("room") String room,
            @JsonProperty("unit") String unit,
            @JsonProperty("shelf") String shelf
    ){
        this.setLocationId(locationId);
        this.setRoom(room);
        this.setUnit(unit);
        this.setShelf(shelf);
    }

    public static LocationDto toLocationDto(Location location){
        return new LocationDto(
                location.getLocationId(),
                location.getRoom(),
                location.getUnit(),
                location.getShelf()
        );
    }

    public Location toLocation() {
        return new Location(
                this.getLocationId(),
                null,
                this.getRoom(),
                this.getUnit(),
                this.getShelf(),
                null
        );
    }
}