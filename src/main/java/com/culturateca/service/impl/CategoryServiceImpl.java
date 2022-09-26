package com.culturateca.service.impl;

import com.culturateca.model.Category;
import com.culturateca.model.repository.CategoryRepository;
import com.culturateca.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category findCategoryById(Long id){return categoryRepository.findById(id).get();}
}
