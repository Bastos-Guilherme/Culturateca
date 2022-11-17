package com.culturateca.controller.dto;

import com.culturateca.model.Category;
import com.culturateca.service.CulturatecaService;
import lombok.*;

import java.util.List;

@Setter
@Getter
public class CategoryDto {

    private Long categoryId;
    private String categoryName;
    private List<MasterPieceDto> masterPiece;

    public CategoryDto(){}

    public CategoryDto toCategoryDto(Category category){return new CategoryDto();}

    public Category toCategory(CulturatecaService culturatecaService){return new Category();}
}
