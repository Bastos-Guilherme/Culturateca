package com.culturateca.controller;

import com.culturateca.controller.dto.CategoryDto;
import com.culturateca.model.Category;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/category/")
public class CategoryController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveCategory(@Valid @RequestBody CategoryDto category){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewCategory(category.toCategory()).getCategoryId();
    }

    @GetMapping("/getById/{categoryId}")
    public Category findCategoryById(@RequestParam Long categoryId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findCategoryById(categoryId);
    }

    @GetMapping("/getAll")
    public List<Category> findAllCategoryes(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllCategories();
    }

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategoryById(@RequestParam Long categoryId){
        culturatecaService.deleteCategoryById(categoryId);
    }

    @PutMapping("/update")
    public Category updateCategory(@Valid @RequestBody Category category){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateCategory(category);
    }
}