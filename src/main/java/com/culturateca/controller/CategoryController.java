package com.culturateca.controller;

import com.culturateca.controller.dto.CategoryDto;
import com.culturateca.model.Category;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveCategory(@Valid @RequestBody CategoryDto category){
        return culturatecaService.saveNewCategory(category.toCategory()).getCategoryId();
    }

    @GetMapping("/{categoryId}")
    public CategoryDto findCategoryById(@RequestParam Long categoryId){
        return CategoryDto.toCategoryDto(culturatecaService.findCategoryById(categoryId));
    }

    @GetMapping()
    public List<CategoryDto> findAllCategories(){
        List<Category> categories = culturatecaService.findAllCategories();
        List<CategoryDto> categoryDtos = new ArrayList<CategoryDto>();
        for (Category category:categories) {
            categoryDtos.add(CategoryDto.toCategoryDto(category));
        }
        return categoryDtos;
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategoryById(@RequestParam Long categoryId){
        culturatecaService.deleteCategoryById(categoryId);
    }

    @PatchMapping()
    public CategoryDto updateCategory(@Valid @RequestBody Category category){
        return CategoryDto.toCategoryDto(culturatecaService.updateCategory(category));
    }

    @PutMapping("/{categoryId}/masterpieces{masterPieceIds}")
    public CategoryDto updateCategoryRelations(@RequestParam(value = "categoryId", required = true) Long categoryId,
                                              @RequestParam(value = "masterPieceIds", required = true) List<Long> masterPieceIds
    ){
        return CategoryDto.toCategoryDto(culturatecaService.updateCategoryRelations(categoryId,masterPieceIds));
    }
}