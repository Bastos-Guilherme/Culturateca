package com.culturateca.controller.dto;

import com.culturateca.model.Address;
import com.culturateca.model.Location;
import com.culturateca.model.Publisher;
import com.culturateca.model.Studio;
import com.culturateca.service.LocationService;
import com.culturateca.service.PublisherService;
import com.culturateca.service.StudioService;
import lombok.AllArgsConstructor;
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
    PublisherService publisherService;

    @Autowired
    StudioService studioService;

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

    public AddressDto(Long addressId,String zipCode,Integer number,String street,String neighborhood,String city,String country,String extraInfo,Long location,Long publisher,Long studio){
        setAddressId(addressId);
        setZipCode(zipCode);
        setNumber(number);
        setStreet(street);
        setNeighborhood(neighborhood);
        setCity(city);
        setCountry(country);
        setExtraInfo(extraInfo);
        setLocation(location);
        setPublisher(publisher);
        setStudio(studio);
    }

    public AddressDto toAddressDto(Address address){
        AddressDto addressDto = new AddressDto(
                address.getAddressId(),
                address.getZipCode(),
                address.getNumber(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getCity(),
                address.getCountry(),
                address.getExtraInfo(),
                address.getLocation().getLocationId(),
                address.getPublisher().getPublisherId(),
                address.getStudio().getStudioId()
        );
        return this;
    }

    public Address toAddress(AddressDto addressDto){
        Address address = new Address();
        Location location = locationService.findLocationById(addressDto.getLocation());
        Publisher publisher = publisherService.findPublisherById(addressDto.getPublisher());
        Studio studio = studioService.findStudioById(addressDto.getStudio());
        address.setAddressId(addressDto.getAddressId());
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