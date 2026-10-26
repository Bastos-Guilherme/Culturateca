package com.social.culturateca.service.implementation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Category;
import com.social.culturateca.model.Copy;
import com.social.culturateca.model.Property;
import com.social.culturateca.model.repository.CanonicRepository;
import com.social.culturateca.service.CanonicService;
import com.social.culturateca.service.CategoryService;
import com.social.culturateca.service.CopyService;
import com.social.culturateca.service.PropertyService;

@Service
public class CanonicServiceImpl implements CanonicService {
    
    @Autowired
    CanonicRepository canonicRepository;

    @Override
    public List<Canonic> findAllCanonics(){
        try {
            return canonicRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Canonic findById(Long id){
        try {
            return canonicRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Canonic> findByCategory(Long categoryId,
            CategoryService categoryService){
        try {
            Category category = categoryService.findById(categoryId);
            if (null == category) {
                throw new RuntimeException("Categoria não encontrada");
            }
            return canonicRepository.findByCategory(category);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Canonic> findAllByProperty(Long propertyId){
        try {
            return canonicRepository.findAllByProperty(propertyId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Canonic createCanonic(Canonic canonic,
            CategoryService categoryService,
            PropertyService propertyService){
        try {
            if (canonic.getId() != null) {
                throw new RuntimeException("Não deve possuir ID específico");
            }

            if (canonic.getName() == null || canonic.getName().isEmpty()) {
                throw new RuntimeException(
                    "Nome é obrigatório"
                );
            }

            if (canonic.getProperty() == null || canonic.getProperty().isEmpty()) {
                throw new RuntimeException(
                    "Property é obrigatória"
                );
            }

            Set<Long> propertyIds = canonic.getProperty().keySet();

            List<Property> properties = propertyService.findAllPropertiesById(propertyIds.stream().toList());

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

            if (null == categoryService.findById(canonic.getCategory().getId())) {
                throw new RuntimeException("ID de Category inexistente");
            }

            return canonicRepository.save(canonic);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Canonic editCanonic(Canonic canonic,
        CategoryService categoryService,
        PropertyService propertyService
    ){
        try {
            if (canonic.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            if (canonic.getName() == null || canonic.getName().isEmpty()) {
                throw new RuntimeException(
                    "Nome é obrigatório"
                );
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

            List<Property> properties = propertyService.findAllPropertiesById(propertyIds.stream().toList());

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

            if (null == categoryService.findById(canonic.getCategory().getId())) {
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
    public void deleteCanonic(Long id){
        try {
            canonicRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateCanonicFromAllCopy(Long canonicId, CopyService copyService) {
        if (!canonicRepository.existsById(canonicId)) {
            throw new RuntimeException("Canonic nao existe");
        }
        Canonic canonic = canonicRepository.findById(canonicId).get();
        List<Copy> copies = copyService.findByCanonic(canonic);
        Canonic updatedCanonic = new Canonic();
        Map<Long, Map<String, Integer>> propertiesCount = new HashMap<>();
        for (Copy copy : copies) {
            for (Long key : copy.getProperty().keySet()) {
                String value = copy.getProperty().get(key);
                if (!propertiesCount.containsKey(key)) {
                    propertiesCount.put(key, new HashMap<>());
                }
                Map<String, Integer> mapOfValues = propertiesCount.get(key);
                if (mapOfValues.containsKey(value)) {
                    mapOfValues.put(value, (mapOfValues.get(value) + 1));
                } else {
                    mapOfValues.put(value, 1);
                }
            }
        }
        Map<Long, String> updatedProperties = new HashMap<>();
        for (Map.Entry<Long, Map<String, Integer>> entryKey: propertiesCount.entrySet()) {
            Long key = entryKey.getKey();
            Map<String, Integer> valueCount = entryKey.getValue();
            String valueMode = null;
            Integer max = 0;
            boolean unique = true;
            for (Map.Entry<String, Integer> entryValue: valueCount.entrySet()) {
                Integer localMax = entryValue.getValue();
                if (localMax > max) {
                    max = localMax;
                    valueMode = entryValue.getKey();
                    unique = true;
                } else if (localMax == max) {
                    unique = false;
                }
            }
            if (unique && valueMode != null) {
                updatedProperties.put(key, valueMode);
            }
        }
        updatedCanonic.setId(canonic.getId());
        updatedCanonic.setCategory(canonic.getCategory());
        updatedCanonic.setName(canonic.getName());
        updatedCanonic.setProperty(updatedProperties);
        canonicRepository.save(updatedCanonic);
    }
}
