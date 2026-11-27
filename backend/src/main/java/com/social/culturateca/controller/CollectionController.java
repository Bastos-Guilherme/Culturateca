package com.social.culturateca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.social.culturateca.service.CuratorService;

@RestController
@RequestMapping("/collection")
public class CollectionController {

  @Autowired
  CollectionService collectionService;

  @Autowired
  CuratorService curatorService;

  @GetMapping
  public List<Collection> getAllCollection() {
    return collectionService.findAllCollections();
  }

  @GetMapping("/curator={email}")
  public ResponseEntity<?> getAllByCurator(@PathVariable("email") String email, Authentication authentication) {
    if (!authentication.getName().equals(email)) {
      return ResponseEntity
          .status(HttpStatus.FORBIDDEN)
          .body("Você não tem permissão para acessar essas coleções.");
    }

    Curator curator = curatorService.findByEmail(email);
    return ResponseEntity.ok(collectionService.findCollectionsByOwner(curator));
  }

  @GetMapping("/id={id}")
  public ResponseEntity<Collection> findById(
      @PathVariable Long id,
      Authentication authentication) {
    Curator curator = curatorService.findByEmail(
        authentication.getName());

    return ResponseEntity.ok(collectionService.findById(id, curator));
  }

  @GetMapping("/name={name}")
  public List<Collection> getByName(@PathVariable("name") String name) {
    return collectionService.findByName(name);
  }

  @PostMapping
  public Collection create(@RequestBody Collection collection) {
    return collectionService.createCollection(collection, curatorService);
  }

  @PutMapping
  public Collection edit(@RequestBody Collection collection) {
    return collectionService.editCollection(collection, curatorService);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCollection(
      @PathVariable Long id,
      Authentication authentication) {
    Curator curator = curatorService.findByEmail(
        authentication.getName());

    collectionService.deleteCollection(id, curator);

    return ResponseEntity.noContent().build();
  }
}
