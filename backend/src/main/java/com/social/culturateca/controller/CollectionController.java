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

import com.social.culturateca.model.Collection;
import com.social.culturateca.model.Curator;
import com.social.culturateca.service.CollectionService;

@RestController
@RequestMapping("/collection")
public class CollectionController {
  
  @Autowired
  CollectionService collectionService;

  @GetMapping
  public List<Collection> getAllByCurator(@RequestBody Curator curator) {
    return collectionService.findCollectionsByOwner(curator);
  }

  @GetMapping("/{id}")
  public Collection getById(@PathVariable Long id) {
    return collectionService.findById(id);
  }
  
  @PostMapping
  public Collection create(@RequestBody Collection collection) {
    return collectionService.createCollection(collection);
  }

  @PutMapping
  public Collection edit(@RequestBody Collection collection) {
    return collectionService.editCollection(collection);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    collectionService.deleteCollection(id);
  }
}
