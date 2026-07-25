package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Collection;
import com.social.culturateca.model.Curator;

public interface CollectionService {

  public Collection createCollection(Collection collection);

  public void deleteCollection(Long id);

  public List<Collection> findCollectionsByOwner(Curator curator);

  public Collection editCollection(Collection collection);

  public Collection findById(Long id);
}
