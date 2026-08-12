package com.social.culturateca.service.implementation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Category;
import com.social.culturateca.model.Property;
import com.social.culturateca.model.repository.CanonicRepository;
import com.social.culturateca.model.repository.CategoryRepository;
import com.social.culturateca.model.repository.PropertyRepository;
import com.social.culturateca.service.CanonicService;

@Service
public class CanonicServiceImpl implements CanonicService {
    
    @Autowired
    CanonicRepository canonicRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Override
    public List<Canonic> canonicAll(){
        try {
            return canonicRepository.findAll();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public Canonic findById(Long id){
        try {
            return canonicRepository.findById(id).get();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public List<Canonic> findByCategory(Long categoryId){
        try {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
            return canonicRepository.findByCategory(category);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public List<Canonic> findAllByProperty(Long propertyId){
        try {
            System.out.println("property ID recebido: " + propertyId);
            return canonicRepository.findAllByProperty(propertyId);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public Canonic createCanonic(Canonic canonic){
        try {
            if (canonic.getId() != null) {
                throw new RuntimeException("Não deve possuir ID específico");
            }

            if (canonic.getProperty() == null || canonic.getProperty().isEmpty()) {
                throw new RuntimeException(
                    "Property é obrigatória"
                );
            }   

            Set<Long> propertyIds = canonic.getProperty().keySet();

            List<Property> properties = propertyRepository.findAllById(propertyIds);

            Set<Long> existingIds = properties.stream()
                    .map(Property::getId)
                    .collect(Collectors.toSet());

            for (Long propertyId : propertyIds) {

                if (!existingIds.contains(propertyId)) {
                    throw new RuntimeException(
                        "Property com ID " + propertyId + " não existe"
                    );
                }
            }

            if (canonic.getCategory() == null || canonic.getCategory().getId() == null) {
                throw new RuntimeException(
                    "Category é obrigatória"
                );
            }

            if (!categoryRepository.existsById(canonic.getCategory().getId())) {
                throw new RuntimeException(
                    "ID de Category inexistente"
                );
            }

            return canonicRepository.save(canonic);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Canonic editCanonic(Canonic canonic){
        try {
            if (canonic.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            if(canonicRepository.findById(canonic.getId()).isEmpty()){
                throw new RuntimeException("Canonic não encontrada");
            }

            if (canonic.getProperty() == null || canonic.getProperty().isEmpty()) {
                throw new RuntimeException(
                    "Property é obrigatória"
                );
            }   

            Set<Long> propertyIds = canonic.getProperty().keySet();

            List<Property> properties = propertyRepository.findAllById(propertyIds);

            Set<Long> existingIds = properties.stream()
                    .map(Property::getId)
                    .collect(Collectors.toSet());

            for (Long propertyId : propertyIds) {

                if (!existingIds.contains(propertyId)) {
                    throw new RuntimeException(
                        "Property com ID " + propertyId + " não existe"
                    );
                }
            }

            if (canonic.getCategory() == null || canonic.getCategory().getId() == null) {
                throw new RuntimeException(
                    "Category é obrigatória"
                );
            }

            if (!categoryRepository.existsById(canonic.getCategory().getId())) {
                throw new RuntimeException(
                    "ID de Category inexistente"
                );
            }

            return canonicRepository.save(canonic);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public void deleteCanonic(Long id){
        try {
            canonicRepository.deleteById(id);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
