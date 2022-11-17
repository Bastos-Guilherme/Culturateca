package com.culturateca.controller.dto;

import com.culturateca.model.Collection;
import com.culturateca.model.Language;
import com.culturateca.service.CulturatecaService;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class CollectionDto {

    private Long collectionId;
    private String collectionName;
    private List<MasterPieceDto> masterPiece;

    public CollectionDto(){};

    public CollectionDto toCollectionDto(Collection collection){return new CollectionDto();}

    public Collection toCollection(CulturatecaService culturatecaService) {return new Collection();}
}