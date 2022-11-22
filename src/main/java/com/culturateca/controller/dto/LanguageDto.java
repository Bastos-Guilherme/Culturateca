package com.culturateca.controller.dto;

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
public class LanguageDto {

    private Long languageId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String languageName;
    @NotBlank
    @NotNull
    @NotEmpty
    private String codeName;
    private List<MasterPieceDto> masterPieces;

    @JsonCreator
    public LanguageDto(
            @JsonProperty("languageId")Long languageId,
            @JsonProperty("languageName")String languageName,
            @JsonProperty("codeName")String codeName,
            @JsonProperty("masterPiece")List<MasterPieceDto> masterPieces
    ){
        this.setLanguageId(languageId);
        this.setLanguageName(languageName);
        this.setCodeName(codeName);
        this.setMasterPieces(masterPieces);
    }

    public static LanguageDto toLanguageDto(Language language){
        return new LanguageDto(
                language.getLanguageId(),
                language.getLanguageName(),
                language.getLanguageCode(),
                null
        );
    }

    public Language toLanguage(CulturatecaService culturatecaService) {
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
        return new Language(
                this.getLanguageId(),
                this.getLanguageName(),
                this.getCodeName(),
                listMasterPieces
        );
    }
}
