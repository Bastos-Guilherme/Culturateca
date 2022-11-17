package com.culturateca.controller.dto;

import com.culturateca.model.Publisher;
import com.culturateca.model.Studio;
import com.culturateca.service.CulturatecaService;
import lombok.*;

@Getter
@Setter
public class StudioDto {

    private Long studioId;
    private String foundationDate;
    private String companyName;
    private Long hq;
    private MasterPieceDto masterPiece;

    public StudioDto(){};

    public StudioDto toStudioDto(Studio studio){return new StudioDto();}

    public Studio toStudio(CulturatecaService culturatecaService) {return new Studio();}
}
