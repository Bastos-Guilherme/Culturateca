package com.social.culturateca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Canonic;

@Repository
public interface CanonicRepository extends JpaRepository<Canonic, Long> {
  
}
