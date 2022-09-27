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
    private Long location;
    private Long publisher;
    private Long studio;

    public AddressDto(){}

    public AddressDto(Long addressId,String state,String zipCode,Integer number,String street,String neighborhood,String city,String country,String extraInfo,Long location,Long publisher,Long studio){
        setAddressId(addressId);
        setZipCode(zipCode);
        setNumber(number);
        setStreet(street);
        setNeighborhood(neighborhood);
        setCity(city);
        setState(state);
        setCountry(country);
        setExtraInfo(extraInfo);
        setLocation(location);
        setPublisher(publisher);
        setStudio(studio);
    }

    public AddressDto toAddressDto(Address address){
        this.setAddressId(address.getAddressId());
        this.setZipCode(address.getZipCode());
        this.setNumber(address.getNumber());
        this.setExtraInfo(address.getExtraInfo());
        this.setStreet(address.getStreet());
        this.setNeighborhood(address.getNeighborhood());
        this.setCity(address.getCity());
        this.setState(address.getState());
        this.setCountry(address.getCountry());
        this.setLocation(address.getLocation().getLocationId());
        this.setPublisher(address.getPublisher().getPublisherId());
        this.setStudio(address.getStudio().getStudioId());
        return this;
    }

    public Address toAddress(LocationService locationService,PublisherService publisherService,StudioService studioService){
        Address address = new Address();
        address.setAddressId(this.getAddressId());
        address.setZipCode(this.getZipCode());
        address.setNumber(this.getNumber());
        address.setStreet(this.getStreet());
        address.setNeighborhood(this.getNeighborhood());
        address.setCity(this.getCity());
        address.setState(this.getState());
        address.setCountry(this.getCountry());
        address.setExtraInfo(this.getExtraInfo());
        address.setLocation(locationService.findLocationById(this.getLocation()));
        address.setPublisher(publisherService.findPublisherById(this.getPublisher()));
        address.setStudio(studioService.findStudioById(this.getStudio()));
        return address;
    }
}