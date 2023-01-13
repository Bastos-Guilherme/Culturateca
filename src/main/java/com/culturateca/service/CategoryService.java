package com.culturateca.service;

import com.culturateca.model.Category;

import java.util.List;

public interface CategoryService {

    Category save(Category category);

    Category findById(Long id);

    List<Category> findAll();

    void deleteById(long id);
}