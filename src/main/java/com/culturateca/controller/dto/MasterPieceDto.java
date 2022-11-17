package com.culturateca.controller.dto;

import com.culturateca.model.Location;
import com.culturateca.model.MasterPiece;
import com.culturateca.service.CulturatecaService;
import lombok.*;

import java.util.List;

@Setter
@Getter
public class MasterPieceDto {

    private Long masterPieceId;
    private String title;
    private List<AuthorDto> author;
    private List<LanguageDto> language;
    private String releaseDate;
    private List<CategoryDto> category;
    private List<CollectionDto> collection;
    private List<PublisherDto> publisher;
    private Integer pages;
    private List<StudioDto> studio;
    private Integer length;
    private String type;
    private List<LocationDto> location;
    private Integer isbn;
    private Integer edition;

    public MasterPieceDto(){};

    public MasterPieceDto toMasterPieceDto(MasterPiece masterPiece){return new MasterPieceDto();}

    public MasterPiece toMasterPiece(CulturatecaService culturatecaService) {return new MasterPiece();}
}