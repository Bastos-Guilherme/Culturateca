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

import com.social.culturateca.model.Copy;
import com.social.culturateca.service.CanonicService;
import com.social.culturateca.service.CopyService;
import com.social.culturateca.service.LocationService;
import com.social.culturateca.service.PropertyService;
import com.social.culturateca.service.CategoryService;

@RestController
@RequestMapping("/copy")
public class CopyController {
    
    @Autowired
    CopyService copyService;
    
    @Autowired
    PropertyService propertyService;
    
    @Autowired
    CanonicService canonicService;
    
    @Autowired
    LocationService locationService;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Copy> getAllCopy(){
        return copyService.findAllCopies();
    }

    @GetMapping("/id={id}")
    public Copy getById(@PathVariable("id") long id){
        return copyService.findById(id);
    }

    @GetMapping("/property={propertyId}")
    public List<Copy> getAllByProperty(@PathVariable("propertyId") long propertyId){
        return copyService.findAllByProperty(propertyId);
    }

    @GetMapping("/canonic={canonicId}")
    public List<Copy> getByCanonic(@PathVariable("canonicId") Long canonicId){
        return copyService.findByCanonic(canonicId, canonicService);
    }

    @PostMapping
    public Copy create(@RequestBody Copy copy){
        return copyService.createCopy(copy, propertyService, canonicService, locationService, categoryService);
    }

    @PutMapping
    public Copy edit(@RequestBody Copy copy){
        return copyService.editCopy(copy, propertyService, canonicService, locationService, categoryService);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        copyService.deleteCopy(id);
    }
}
