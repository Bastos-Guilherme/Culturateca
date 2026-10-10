package com.social.culturateca.service;
import java.util.List;

import com.social.culturateca.model.Property;

public interface PropertyService {
    
    public List<Property> findAllProperties();

    public List<Property> findAllPropertiesById(List<Long> ids);

    public Property findById(Long id);

    public List<Property> findByName(String name);

    public Property createProperty(Property property);

    public Property editProperty(Property property);
    
    public void deleteProperty(Long id);
}
