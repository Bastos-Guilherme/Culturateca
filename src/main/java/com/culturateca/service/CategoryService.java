package com.culturateca.service;

import com.culturateca.model.Category;

import java.util.List;

public interface CategoryService {

    Category saveNew(Category category);

    Category findById(Long id);

    List<Category> findAll();

    void deleteById(long id);
}