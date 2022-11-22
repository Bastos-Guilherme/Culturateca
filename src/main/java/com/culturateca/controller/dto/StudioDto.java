package com.culturateca.controller.dto;

import com.culturateca.model.Address;
import com.culturateca.model.MasterPiece;
import com.culturateca.model.Publisher;
import com.culturateca.model.Studio;
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

@Getter
@Setter
public class StudioDto {

    private Long studioId;
    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDate foundationDate;
    @NotBlank
    @NotNull
    @NotEmpty
    private String companyName;
    private AddressDto hq;
    private List<MasterPieceDto> masterPieces;

    @JsonCreator
    public StudioDto(
            @JsonProperty("studioId") Long studioId,
            @JsonProperty("foundationDate") LocalDate foundationDate,
            @JsonProperty("companyName") String companyName,
            @JsonProperty("hq") AddressDto hq,
            @JsonProperty("masterPiece") List<MasterPieceDto> masterPieces
    ){
        this.setStudioId(studioId);
        this.setFoundationDate(foundationDate);
        this.setCompanyName(companyName);
        this.setHq(hq);
        this.setMasterPieces(masterPieces);
    }

    public static StudioDto toStudioDto(Studio studio){
        return new StudioDto(
                studio.getStudioId(),
                studio.getFoundationDate(),
                studio.getCompanyName(),
                null,
                null
        );
    }

    public Studio toStudio(CulturatecaService culturatecaService) {
        Address addressFromDto;
        if (this.getHq() == null){
            addressFromDto = null;
        } else if (this.getHq().getAddressId() == null) {
            addressFromDto = this.getHq().toAddress(culturatecaService);
        } else {
            addressFromDto = culturatecaService.findAddressById(this.getHq().getAddressId());
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
        return new Studio(
                this.getStudioId(),
                this.getFoundationDate(),
                this.getCompanyName(),
                addressFromDto,
                listMasterPieces
        );
    }
}
