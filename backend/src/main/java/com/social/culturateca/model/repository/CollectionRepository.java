package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Collection;
import com.social.culturateca.model.Curator;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
  
  List<Collection> findByName(String name);

  List<Collection> findAllByCurator(Curator curator);
}
