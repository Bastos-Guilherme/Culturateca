package com.culturateca.controller.dto;

import com.culturateca.model.Location;
import com.culturateca.model.MasterPiece;
import com.culturateca.model.Publisher;
import com.culturateca.service.CulturatecaService;
import lombok.*;

@Setter
@Getter
public class PublisherDto {

    private Long publisherId;
    private String publisherName;
    private AddressDto address;
    private String foundationDate;
    private MasterPieceDto masterPiece;

    public PublisherDto(){};

    public PublisherDto toPublisherDto(Publisher publisher){return new PublisherDto();}

    public Publisher toPublisher(CulturatecaService culturatecaService) {return new Publisher();}
}
