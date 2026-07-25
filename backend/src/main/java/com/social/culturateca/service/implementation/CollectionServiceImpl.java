package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Collection;
import com.social.culturateca.model.Curator;
import com.social.culturateca.model.repository.CollectionRepository;
import com.social.culturateca.model.repository.CuratorRepository;
import com.social.culturateca.service.CollectionService;

@Service
public class CollectionServiceImpl implements CollectionService {
  List<String> BLACKLIST = List.of(
    "armas",
    "drogas"
  );

  @Autowired
  CollectionRepository collectionRepository;

  @Autowired
  CuratorRepository curatorRepository;

  @Override
  public Collection createCollection(Collection collection) {
    try {
      //collection.setCurator(curatorRepository.findById(collection.getCurator().getEmail()).get());
      if (collection.getId() != null) {
        throw new RuntimeException();
      }
      if (!curatorRepository.existsById(collection.getCurator().getEmail())) {
        throw new RuntimeException();
      }
      if (BLACKLIST.contains(collection.getName().toLowerCase())) {
        throw new RuntimeException();
      }
      return collectionRepository.save(collection);
    } catch (Exception e) {
      // TODO: handle exception
      return null;
    }
  }

  @Override
  public void deleteCollection(Long id) {
    try {
      collectionRepository.deleteById(id);
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  @Override
  public Collection editCollection(Collection collection) {
    try {
      if (BLACKLIST.contains(collection.getName().toLowerCase())) {
        throw new RuntimeException();
      }
      return collectionRepository.save(collection);
    } catch (Exception e) {
      // TODO: handle exception
      return null;
    }
  }

  @Override
  public List<Collection> findCollectionsByOwner(Curator curator) {
    try {
      if (curator == null) {
        throw new RuntimeException();
      }
      return collectionRepository.findAllByCurator(curator);
    } catch (Exception e) {
      // TODO: handle exception
      return null;
    }
  }

  @Override
  public Collection findById(Long id) {
    try {
      return collectionRepository.findById(id).get();
    } catch (Exception e) {
      // TODO: handle exception
      return null;
    }
  }
}
