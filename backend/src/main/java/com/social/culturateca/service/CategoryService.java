package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Category;

public interface CategoryService {

    public List<Category> categoryAll();

    public Category createCategory(Category category);

    public Category editCategory(Category category);
    
    public void deleteCategory(Long id);

    public Category findById(Long id);

    public List<Category> findByName(String name);
}
