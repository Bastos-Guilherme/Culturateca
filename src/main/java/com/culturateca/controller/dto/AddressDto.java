package com.culturateca.controller.dto;

import com.culturateca.model.Address;
import com.culturateca.service.CulturatecaService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressDto {

    private Long addressId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String zipCode;
    @NotNull
    private Integer number;
    @NotBlank
    @NotNull
    @NotEmpty
    private String street;
    private String neighborhood;
    @NotBlank
    @NotNull
    @NotEmpty
    private String city;
    @NotBlank
    @NotNull
    @NotEmpty
    private String state;
    @NotBlank
    @NotNull
    @NotEmpty
    private String country;
    private String extraInfo;

    @JsonCreator
    public AddressDto(
            @JsonProperty("addressId") Long addressId,
            @JsonProperty("state") String state,
            @JsonProperty("zipCode") String zipCode,
            @JsonProperty("number") Integer number,
            @JsonProperty("street") String street,
            @JsonProperty("neighborhood") String neighborhood,
            @JsonProperty("city") String city,
            @JsonProperty("country") String country,
            @JsonProperty("extraInfo") String extraInfo
    ){
        this.setAddressId(addressId);
        this.setZipCode(zipCode);
        this.setNumber(number);
        this.setStreet(street);
        this.setNeighborhood(neighborhood);
        this.setCity(city);
        this.setState(state);
        this.setCountry(country);
        this.setExtraInfo(extraInfo);
    }

    public static AddressDto toAddressDto(Address address, CulturatecaService culturatecaService) {
            return new AddressDto(
                    address.getAddressId(),
                    address.getState(),
                    address.getZipCode(),
                    address.getNumber(),
                    address.getStreet(),
                    address.getNeighborhood(),
                    address.getCity(),
                    address.getCountry(),
                    address.getExtraInfo()
            );
    }

    public Address toAddress() {
        return new Address(
                this.getAddressId(),
                this.getZipCode(),
                this.getNumber(),
                this.getStreet(),
                this.getNeighborhood(),
                this.getCity(),
                this.getState(),
                this.getCountry(),
                this.getExtraInfo(),
                null,
                null,
                null
        );
    }
}