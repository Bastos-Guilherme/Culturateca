package com.social.culturateca.service.implementation;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Property;
import com.social.culturateca.model.repository.PropertyRepository;
import com.social.culturateca.service.PropertyService;

@Service
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyRepository propertyRepository;

    @Override
    public List<Property> findAllProperties(){
        try {
            return propertyRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Property> findAllPropertiesById(List<Long> ids){
        try {
            return propertyRepository.findAllById(ids);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Property findById(Long id){
        try {
            return propertyRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Property> findByName(String name){
        try {
            return propertyRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Property createProperty(Property property){
        try {
            if (property.getId() != null) {
                throw new RuntimeException("ID não deve ser específicado");
            }

            if (!propertyRepository.findByNameContainingIgnoreCase(property.getName()).isEmpty()) {
                throw new RuntimeException();
            }

            return propertyRepository.save(property);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Property editProperty(Property property){
        try {
            if (property.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            if(propertyRepository.findById(property.getId()).isEmpty()){
                throw new RuntimeException("Property não encontrada");
            }

            if (!propertyRepository.findByNameContainingIgnoreCase(property.getName()).isEmpty()) {
                throw new RuntimeException("Property ja cadastrada");
            }

            return propertyRepository.save(property);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteProperty(Long id){
        try {
            propertyRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
