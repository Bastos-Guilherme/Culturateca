package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
  
  List<Property> findByName(String name);
}
