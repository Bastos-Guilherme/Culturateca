package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Category;
import com.social.culturateca.model.Curator;
import com.social.culturateca.model.repository.CategoryRepository;
import com.social.culturateca.service.CategoryService;
import com.social.culturateca.service.CuratorService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAllCategories() {
        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Category> findByName(String name) {
        try {
            return categoryRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Category findById(Long id) {
        try {
            return categoryRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Category createCategory(Category category, CuratorService curatorService) {
        try {
            if (category.getId() != null) {
                throw new RuntimeException("ID não deve ser especificado");
            }

            if (!categoryRepository.findByNameContainingIgnoreCase(category.getName()).isEmpty()) {
                throw new RuntimeException("Category já existe");
            }

            if (category.getCurator() == null || category.getCurator().getId() == null) {
                throw new RuntimeException("Curator é obrigatório");
            }

            Curator curator = curatorService.findById(category.getCurator().getId());

            if (curator == null) {
                throw new RuntimeException("Curator não encontrado");
            }

            category.setCurator(curator);

            return categoryRepository.save(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Category editCategory(Category category, CuratorService curatorService) {
        try {
            if (category.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            if (categoryRepository.findById(category.getId()).isEmpty()) {
                throw new RuntimeException("Category não encontrada");
            }

            if (!categoryRepository.findByNameContainingIgnoreCase(category.getName()).isEmpty()) {
                throw new RuntimeException();
            }

            if (category.getCurator() == null || category.getCurator().getId() == null) {
                throw new RuntimeException("Curator é obrigatório");
            }

            Curator curator = curatorService.findById(category.getCurator().getId());

            if (curator == null) {
                throw new RuntimeException("Curator não encontrado");
            }

            category.setCurator(curator);

            return categoryRepository.save(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
