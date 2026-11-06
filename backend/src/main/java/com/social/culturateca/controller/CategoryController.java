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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.model.Category;
import com.social.culturateca.service.CategoryService;
import com.social.culturateca.service.CopyService;
import com.social.culturateca.service.CuratorService;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CuratorService curatorService;

    @Autowired 
    CopyService copyService;

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/id={id}")
    public Category getById(@PathVariable("id") long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/name")
    public List<Category> getByName(@RequestParam String name, @RequestParam Long curatorId) {
        return categoryService.findByName(name, curatorId);
    }

    @GetMapping("/curator={curatorId}")
    public List<Category> getAllByCurator(@PathVariable("curatorId") Long curatorId) {
        return categoryService.findAllByCurator(curatorId);
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.createCategory(category, curatorService);
    }

    @PutMapping
    public Category edit(@RequestBody Category category) {
        return categoryService.editCategory(category, curatorService);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id, copyService);
    }
}
