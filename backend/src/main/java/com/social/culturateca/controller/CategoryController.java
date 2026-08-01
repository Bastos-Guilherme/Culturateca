package com.social.culturateca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.model.Category;
import com.social.culturateca.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategory(){
        return categoryService.categoryAll();
    }

    @GetMapping("/id={id}")
    public Category getById(@PathVariable long id){
        return categoryService.findById(id);
    }

    @GetMapping("/name={name}")
    public List<Category> getByName(@PathVariable String name){
        return categoryService.findByName(name);
    }

    @PostMapping
    public Category create(@RequestBody Category category){
        System.out.println("ta passano");
        return categoryService.createCategory(category);
    }

    @PutMapping
    public Category edit(@RequestBody Category category){
        return categoryService.editCategory(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
