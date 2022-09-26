package com.culturateca.controller.dto;

import com.culturateca.model.Address;
import com.culturateca.model.Location;
import com.culturateca.model.Publisher;
import com.culturateca.model.Studio;
import com.culturateca.service.LocationService;
import com.culturateca.service.PublisherService;
import com.culturateca.service.StudioService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressDto {

    @Autowired
    StudioService studioService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    LocationService locationService;

    @NotBlank
    @NotNull
    @NotEmpty
    private Long addressId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String zipCode;
    @NotBlank
    @NotNull
    @NotEmpty
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
    private String country;
    private String extraInfo;
    @NotBlank
    @NotEmpty
    private Long location;
    @NotBlank
    @NotEmpty
    private Long publisher;
    @NotBlank
    @NotEmpty
    private Long studio;

    public AddressDto(String zipCode, Integer number, String street, String neighborhood, String city, String country, String extraInfo, Long location){
        this.addressId = null;
        this.zipCode = zipCode;
        this.number = number;
        this.street = street;
        this.neighborhood = neighborhood;
        this.city = city;
        this.country = country;
        this.extraInfo = extraInfo;
        this.location = location;
        this.publisher = null;
        this.studio = null;
    }

    public AddressDto toAddressDto(Address address){
        this.setAddressId(address.getAddressId());
        this.setZipCode(address.getZipCode());
        this.setNumber(address.getNumber());
        this.setStreet(address.getStreet());
        this.setNeighborhood(address.getNeighborhood());
        this.setCity(address.getCity());
        this.setCountry(address.getCountry());
        this.setExtraInfo(address.getExtraInfo());
        this.setLocation(address.getLocation().getLocationId());
        this.setPublisher(address.getPublisher().getPublisherId());
        this.setStudio(address.getStudio().getStudioId());
        return this;
    }

    public Address toAddress(AddressDto addressDto){
        Address address = new Address();
        Location location = locationService.findLocationById(addressDto.getLocation());
        Publisher publisher = publisherService.findPublisherById(addressDto.getPublisher());
        Studio studio = studioService.findStudioById(addressDto.getStudio());
        address.setAddressId(addressDto.getAddressId());
        address.setZipCode(addressDto.getZipCode());
        address.setZipCode(addressDto.getZipCode());
        address.setNumber(addressDto.getNumber());
        address.setStreet(addressDto.getStreet());
        address.setNeighborhood(addressDto.getNeighborhood());
        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setExtraInfo(addressDto.getExtraInfo());
        address.setLocation(location);
        address.setPublisher(publisher);
        address.setStudio(studio);
        return address;
    }
}