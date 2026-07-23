package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Curator;

@Repository
public interface CuratorRepository extends JpaRepository<Curator, String> {
  
  List<Curator> findByName(String name);
}
