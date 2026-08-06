package com.social.culturateca.service;
import java.util.List;

import com.social.culturateca.model.Property;

public interface PropertyService {
    
    public List<Property> propertyAll();

    public Property createProperty(Property property);

    public Property editProperty(Property property);
    
    public void deleteProperty(Long id);

    public Property findById(Long id);

    public List<Property> findByName(String name);
}
