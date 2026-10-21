package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Collection;
import com.social.culturateca.model.Curator;

public interface CollectionService {

  public List<Collection> findAllCollections();

  public Collection createCollection(Collection collection, CuratorService curatorService);

  public void deleteCollection(Long id);

  public List<Collection> findCollectionsByOwner(Curator curator);

  public Collection editCollection(Collection collection, CuratorService curatorService);

  public Collection findById(Long id);

  public List<Collection> findByName(String name);
}
