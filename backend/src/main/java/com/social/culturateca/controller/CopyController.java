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
import com.social.culturateca.service.CopyService;

@RestController
@RequestMapping("/copy")
public class CopyController {
    
    @Autowired
    CopyService copyService;

    @GetMapping
    public List<Copy> getAllCopy(){
        return copyService.copyAll();
    }

    @GetMapping("/id={id}")
    public Copy getById(@PathVariable long id){
        return copyService.findById(id);
    }

    @GetMapping("/property={propertyId}")
    public List<Copy> getAllByProperty(@PathVariable long propertyId){
        return copyService.findAllByProperty(propertyId);
    }

    @GetMapping("/canonic={canonicId}")
    public List<Copy> getByCanonic(@PathVariable Long canonicId){
        return copyService.findByCanonic(canonicId);
    }

    @PostMapping
    public Copy create(@RequestBody Copy copy){
        return copyService.createCopy(copy);
    }

    @PutMapping
    public Copy edit(@RequestBody Copy copy){
        return copyService.editCopy(copy);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        copyService.deleteCopy(id);
    }

}
