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

import com.social.culturateca.model.Canonic;
import com.social.culturateca.service.CanonicService;

@RestController
@RequestMapping("/canonic")
public class CanonicController {
    
    @Autowired
    CanonicService canonicService;

    @GetMapping
    public List<Canonic> getAllCanonic(){
        return canonicService.canonicAll();
    }

    @GetMapping("/id={id}")
    public Canonic getById(@PathVariable long id){
        return canonicService.findById(id);
    }

    @GetMapping("/category={categoryId}")
    public List<Canonic> getByCategory(@PathVariable Long categoryId){
        return canonicService.findByCategory(categoryId);
    }

    @GetMapping("/property={propertyId}")
    public List<Canonic> getAllByProperty(@PathVariable long propertyId){
        return canonicService.findAllByProperty(propertyId);
    }

    @PostMapping
    public Canonic create(@RequestBody Canonic canonic){
        return canonicService.createCanonic(canonic);
    }

    @PutMapping
    public Canonic edit(@RequestBody Canonic canonic){
        return canonicService.editCanonic(canonic);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        canonicService.deleteCanonic(id);
    }
}
