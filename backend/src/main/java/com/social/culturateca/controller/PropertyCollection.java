package com.social.culturateca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.model.Property;
import com.social.culturateca.service.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyCollection {
    @Autowired
    PropertyService propertyService;

    @GetMapping
    public List<Property> getAllProperty(){
        return propertyService.propertyAll();
    }

    @GetMapping("/id={id}")
    public Property getById(@PathVariable long id){
        return propertyService.findById(id);
    }

    @GetMapping("/name={name}")
    public List<Property> getByName(@PathVariable String name){
        return propertyService.findByName(name);
    }

    @PostMapping
    public Property create(@RequestBody Property property){
        return propertyService.createProperty(property);
    }

    @PutMapping
    public Property edit(@RequestBody Property property){
        return propertyService.editProperty(property);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        propertyService.deleteProperty(id);
    }
}
