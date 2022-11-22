package com.culturateca.controller.dto;

import com.culturateca.model.Category;
import com.culturateca.model.MasterPiece;
import com.culturateca.service.CulturatecaService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class CategoryDto {

    private Long categoryId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String categoryName;
    private List<MasterPieceDto> masterPieces;

    @JsonCreator
    public CategoryDto(
            @JsonProperty("categoryId") Long categoryId,
            @JsonProperty("categoryName") String categoryName,
            @JsonProperty("masterPieces") List<MasterPieceDto> masterPieces
    ){
        this.setCategoryId(categoryId);
        this.setCategoryName(categoryName);
        this.setMasterPieces(masterPieces);
    }

    public static CategoryDto toCategoryDto(Category category){
        return new CategoryDto(
                category.getCategoryId(),
                category.getCategoryName(),
                null
        );
    }

    public Category toCategory(CulturatecaService culturatecaService){
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
        return new Category(
                this.getCategoryId(),
                this.getCategoryName(),
                listMasterPieces
        );
    }
}
