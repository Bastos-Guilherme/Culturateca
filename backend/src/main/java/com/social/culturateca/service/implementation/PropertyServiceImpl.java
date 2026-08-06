package com.social.culturateca.service.implementation;

import java.util.List;

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
    public List<Property> propertyAll(){
        try {
            return propertyRepository.findAll();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public List<Property> findByName(String name){
        try {
            return propertyRepository.findByName(name);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public Property findById(Long id){
        try {
            return propertyRepository.findById(id).get();
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public Property createProperty(Property property){
        try {
            if (property.getId() != null) {
                throw new RuntimeException();
            }
            if (!propertyRepository.findByName(property.getName()).isEmpty()) {
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
            return propertyRepository.save(property);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    @Override
    public void deleteProperty(Long id){
        try {
            propertyRepository.deleteById(id);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
