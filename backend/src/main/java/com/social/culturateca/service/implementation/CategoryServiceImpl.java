package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Category;
import com.social.culturateca.model.repository.CategoryRepository;
import com.social.culturateca.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> categoryAll(){
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public List<Category> findByName(String name){
        try {
            return categoryRepository.findByName(name);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public Category findById(Long id){
        try {
            return categoryRepository.findById(id).get();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public Category createCategory(Category category){
        try {
            if (category.getId() != null) {
                throw new RuntimeException();
            }
            if (!categoryRepository.findByName(category.getName()).isEmpty()) {
                throw new RuntimeException();
            }
            return categoryRepository.save(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Category editCategory(Category category){
        try {
            return categoryRepository.save(category);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public void deleteCategory(Long id){
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
