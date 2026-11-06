package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.social.culturateca.model.Category;
import com.social.culturateca.model.Curator;
import com.social.culturateca.model.repository.CategoryRepository;
import com.social.culturateca.service.CategoryService;
import com.social.culturateca.service.CopyService;
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
    public List<Category> findByName(String name, Long curatorId) {
        try {
            return categoryRepository.findByNameContainingIgnoreCaseAndCurator_Id(
                    name, curatorId);
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
    public List<Category> findAllByCurator(Long curatorId) {
        return categoryRepository.findAllByCurator_Id(curatorId);
    }

    @Override
    public Category createCategory(Category category, CuratorService curatorService) {
        try {
            if (category.getId() != null) {
                throw new RuntimeException("ID não deve ser especificado");
            }

            if (category.getCurator() == null || category.getCurator().getId() == null) {
                throw new RuntimeException("Curator é obrigatório");
            }

            Curator curator = curatorService.findById(category.getCurator().getId());

            if (curator == null) {
                throw new RuntimeException("Curator não encontrado");
            }

            if (!categoryRepository.findByNameContainingIgnoreCaseAndCurator_Id(category.getName(), curator.getId())
                    .isEmpty()) {
                throw new RuntimeException("Category já existe para este curator");
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

            if (category.getCurator() == null || category.getCurator().getId() == null) {
                throw new RuntimeException("Curator é obrigatório");
            }

            Curator curator = curatorService.findById(category.getCurator().getId());

            if (curator == null) {
                throw new RuntimeException("Curator não encontrado");
            }

            if (!categoryRepository.findByNameContainingIgnoreCaseAndCurator_IdAndIdNot(category.getName(),
                    curator.getId(), category.getId()).isEmpty()) {
                throw new RuntimeException("Category já existe para este curator");
            }

            category.setCurator(curator);

            return categoryRepository.save(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteCategory(Long id, CopyService copyService) {
        if (copyService.existsByCategory(id)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Não é possível excluir uma categoria vinculada a cópias.");
        }

        categoryRepository.deleteById(id);
    }
}
