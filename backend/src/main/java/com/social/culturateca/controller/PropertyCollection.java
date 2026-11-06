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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.model.Property;
import com.social.culturateca.service.CuratorService;
import com.social.culturateca.service.PropertyService;

@RestController
@RequestMapping("/property")
public class PropertyCollection {
    @Autowired
    PropertyService propertyService;

    @Autowired
    CuratorService curatorService;

    @GetMapping
    public List<Property> getAllProperty() {
        return propertyService.findAllProperties();
    }

    @GetMapping("/id={id}")
    public Property getById(@PathVariable("id") long id) {
        return propertyService.findById(id);
    }

    @GetMapping("/name")
    public List<Property> getByName(@RequestParam String name, @RequestParam Long curatorId) {
        return propertyService.findByName(name, curatorId);
    }

    @GetMapping("/curator={curatorId}")
    public List<Property> getAllByCurator(@PathVariable("curatorId") Long curatorId) {
        return propertyService.findAllByCurator(curatorId);
    }

    @PostMapping
    public Property create(@RequestBody Property property) {
        return propertyService.createProperty(property, curatorService);
    }

    @PutMapping
    public Property edit(@RequestBody Property property) {
        return propertyService.editProperty(property, curatorService);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        propertyService.deleteProperty(id);
    }
}
