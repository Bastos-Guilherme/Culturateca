package com.culturateca.controller.dto;

import com.culturateca.model.Address;
import com.culturateca.model.MasterPiece;
import com.culturateca.model.Publisher;
import com.culturateca.service.CulturatecaService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class PublisherDto {

    private Long publisherId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String publisherName;
    private AddressDto address;
    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDate foundationDate;
    private List<MasterPieceDto> masterPieces;

    @JsonCreator
    public PublisherDto(
            @JsonProperty("publisherId") Long publisherId,
            @JsonProperty("publisherName") String publisherName,
            @JsonProperty("address") AddressDto address,
            @JsonProperty("foundationDate") LocalDate foundationDate,
            @JsonProperty("masterPiece") List<MasterPieceDto> masterPieces
    ){
        this.setPublisherId(publisherId);
        this.setPublisherName(publisherName);
        this.setAddress(address);
        this.setFoundationDate(foundationDate);
        this.setMasterPieces(masterPieces);
    }

    public static PublisherDto toPublisherDto(Publisher publisher){
        return new PublisherDto(
                publisher.getPublisherId(),
                publisher.getPublisherName(),
                null,
                publisher.getFoundationDate(),
                null
        );
    }

    public Publisher toPublisher(CulturatecaService culturatecaService) {
        Address addressFromDto;
        if (this.getAddress() == null){
            addressFromDto = null;
        } else if (this.getAddress().getAddressId() == null) {
            addressFromDto = this.getAddress().toAddress(culturatecaService);
        } else {
            addressFromDto = culturatecaService.findAddressById(this.getAddress().getAddressId());
        }
        List<MasterPiece> listMasterPieces = null;
        if (this.getMasterPieces() != null) {
            List<MasterPieceDto> listMasterPiecesDto = this.getMasterPieces();
            for (MasterPieceDto m : listMasterPiecesDto) {
                if (m.getMasterPieceId() == null) {
                    listMasterPieces.add(m.toMasterPiece(culturatecaService));
                } else {
                    listMasterPieces.add(culturatecaService.findMasterPieceById(m.getMasterPieceId()));
                }
            }
        }
        return new Publisher(
                this.getPublisherId(),
                this.getPublisherName(),
                addressFromDto,
                this.getFoundationDate(),
                listMasterPieces
        );
    }
}
