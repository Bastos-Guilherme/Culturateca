package com.culturateca.controller.dto;

import com.culturateca.model.Collection;
import com.culturateca.model.Language;
import com.culturateca.model.MasterPiece;
import com.culturateca.service.CulturatecaService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CollectionDto {

    private Long collectionId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String collectionName;
    private List<MasterPieceDto> masterPieces;

    @JsonCreator
    public CollectionDto(
            @JsonProperty("collectionId") Long collectionId,
            @JsonProperty("collectionName") String collectionName,
            @JsonProperty("masterPieces") List<MasterPieceDto> masterPieces
    ){
        this.setCollectionId(collectionId);
        this.setCollectionName(collectionName);
        this.setMasterPieces(masterPieces);
    }

    public static CollectionDto toCollectionDto(Collection collection){
        return new CollectionDto(
                collection.getCollectionId(),
                collection.getCollectionName(),
                null
        );
    }

    public Collection toCollection(CulturatecaService culturatecaService) {
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
        return new Collection(
                this.getCollectionId(),
                this.getCollectionName(),
                listMasterPieces
        );
    }
}