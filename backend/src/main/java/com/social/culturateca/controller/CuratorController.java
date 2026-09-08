package com.social.culturateca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.culturateca.model.Curator;
import com.social.culturateca.service.CuratorService;

@RestController
@RequestMapping("/curator")
public class CuratorController {
    
    @Autowired
    CuratorService curatorService;

    @GetMapping
    public List<Curator> getAllCurator(){
        return curatorService.curatorAll();
    }

    @GetMapping("/me")
    public Curator getCurrentCurator(Authentication authentication){
        String email = authentication.getName();

        return curatorService.findByEmail(email);
    }

    @GetMapping("/email={email}")
    public Curator getById(@PathVariable String email){
        return curatorService.findByEmail(email);
    }

    @GetMapping("/name={name}")
    public List<Curator> getByName(@PathVariable String name){
        return curatorService.findByName(name);
    }

    @PostMapping("/register")
    public Curator create(@RequestBody Curator curator){
        return curatorService.createCurator(curator);
    }

    @PutMapping
    public Curator edit(@RequestBody Curator curator){
        return curatorService.editCurator(curator);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String email){
        curatorService.deleteCurator(email);
    }

}
