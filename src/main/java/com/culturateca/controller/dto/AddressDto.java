package com.culturateca.controller.dto;

import com.culturateca.model.Address;
import com.culturateca.model.Location;
import com.culturateca.model.Publisher;
import com.culturateca.model.Studio;
import com.culturateca.service.CulturatecaService;
import com.culturateca.service.LocationService;
import com.culturateca.service.PublisherService;
import com.culturateca.service.StudioService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private LocationDto location;
    private PublisherDto publisher;
    private StudioDto studio;

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
            @JsonProperty("extraInfo") String extraInfo,
            @JsonProperty("location") LocationDto location,
            @JsonProperty("publisher") PublisherDto publisher,
            @JsonProperty("studio") StudioDto studio){
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
        this.setLocation(new LocationDto().toLocationDto(address.getLocation()));
        this.setPublisher(new PublisherDto().toPublisherDto(address.getPublisher()));
        this.setStudio(new StudioDto().toStudioDto(address.getStudio()));
        return this;
    }

    public Address toAddress(CulturatecaService culturatecaService){
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
        if (this.getLocation() == null){
            address.setLocation(null);
        } else if (this.getLocation().getLocationId() == null) {
            address.setLocation(this.getLocation().toLocation(culturatecaService));
        } else {
            address.setLocation(culturatecaService.findLocationById(this.getLocation().getLocationId()));
        }
        if (this.getPublisher() == null){
            address.setPublisher(null);
        } else if (this.getPublisher().getPublisherId() == null) {
            address.setPublisher(this.getPublisher().toPublisher(culturatecaService));
        } else {
            address.setPublisher(culturatecaService.findPublisherById(this.getPublisher().getPublisherId()));
        }
        if (this.getStudio() == null){
            address.setStudio(null);
        } else if (this.getStudio().getStudioId() == null) {
            address.setStudio(this.getStudio().toStudio(culturatecaService));
        } else {
            address.setStudio(culturatecaService.findStudioById(this.getStudio().getStudioId()));
        }
        return address;
    }
}