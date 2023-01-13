package com.culturateca.controller.dto;

import com.culturateca.model.Category;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CategoryDto {

    private Long categoryId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String categoryName;

    @JsonCreator
    public CategoryDto(
            @JsonProperty("categoryId") Long categoryId,
            @JsonProperty("categoryName") String categoryName
    ){
        this.setCategoryId(categoryId);
        this.setCategoryName(categoryName);
    }

    public static CategoryDto toCategoryDto(Category category){
        return new CategoryDto(
                category.getCategoryId(),
                category.getCategoryName()
        );
    }

    public Category toCategory(){
        return new Category(
                this.getCategoryId(),
                this.getCategoryName(),
                null
        );
    }
}
