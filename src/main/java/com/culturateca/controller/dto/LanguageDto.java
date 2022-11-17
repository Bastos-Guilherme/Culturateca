package com.culturateca.controller.dto;

import com.culturateca.model.Language;
import com.culturateca.service.CulturatecaService;
import lombok.*;

@Getter
@Setter
public class LanguageDto {

    private Long languageId;
    private String languageName;
    private String codeName;
    private MasterPieceDto masterPiece;

    public LanguageDto(){};

    public LanguageDto toLanguageDto(Language language){return new LanguageDto();}

    public Language toLanguage(CulturatecaService culturatecaService) {return new Language();}
}
