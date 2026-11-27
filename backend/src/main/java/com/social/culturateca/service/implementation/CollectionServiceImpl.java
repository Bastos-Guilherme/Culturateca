package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Collection;
import com.social.culturateca.model.Curator;
import com.social.culturateca.model.repository.CollectionRepository;
import com.social.culturateca.service.CollectionService;
import com.social.culturateca.service.CuratorService;

@Service
public class CollectionServiceImpl implements CollectionService {

  List<String> BLACKLIST = List.of(
      "armas",
      "drogas");

  @Autowired
  CollectionRepository collectionRepository;

  @Override
  public List<Collection> findAllCollections() {
    try {
      return collectionRepository.findAll();
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public Collection createCollection(Collection collection, CuratorService curatorService) {
    try {
      if (collection.getId() != null) {
        throw new RuntimeException("ID não deve ser especificado");
      }

      Curator curator = curatorService.findByEmail(collection.getCurator().getEmail());
      if (curator == null) {
        throw new RuntimeException("Curator não encontrado");
      }
      collection.setCurator(curator);

      if (BLACKLIST.contains(collection.getName().toLowerCase())) {
        throw new RuntimeException("Nome de coleção não permitido");
      }

      return collectionRepository.save(collection);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  @Override
  public void deleteCollection(Long id, Curator curator) {
    Collection collection = findCollectionByOwner(id, curator);

    if (collection.getCopies() != null &&
        !collection.getCopies().isEmpty()) {
      throw new RuntimeException(
          "Não é possível excluir uma coleção que possui cópias vinculadas.");
    }

    collectionRepository.delete(collection);
  }

  @Override
  public Collection editCollection(Collection collection, CuratorService curatorService) {
    if (collection.getId() == null) {
      throw new RuntimeException("ID não pode ser nulo");
    }

    if (collectionRepository.findById(collection.getId()).isEmpty()) {
      throw new RuntimeException("Collection não encontrada");
    }

    Curator curator = curatorService.findById(collection.getCurator().getId());
    if (curator == null) {
      throw new RuntimeException("Curator não encontrado");
    }
    collection.setCurator(curator);

    if (BLACKLIST.contains(collection.getName().toLowerCase())) {
      throw new RuntimeException("Nome de coleção não permitido");
    }

    if (collection.getIsPublic() == null) {
      collection.setIsPublic(true);
    }

    return collectionRepository.save(collection);
  }

  @Override
  public List<Collection> findCollectionsByOwner(Curator curator) {
    try {
      if (curator == null) {
        throw new RuntimeException();
      }

      return collectionRepository.findAllByCurator(curator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

  private Collection findCollectionByOwner(Long id, Curator curator) {
    if (curator == null || curator.getId() == null) {
      throw new RuntimeException("Curador não autenticado");
    }

    return collectionRepository
        .findByIdAndCurator_Id(id, curator.getId())
        .orElseThrow(() -> new RuntimeException(
            "Coleção não encontrada ou acesso não autorizado"));
  }

  @Override
  public Collection findById(Long id, Curator curator) {
    return findCollectionByOwner(id, curator);
  }

  @Override
  public List<Collection> findByName(String name) {
    try {
      return collectionRepository.findByNameContainingIgnoreCase(name);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return null;
    }
  }

}
