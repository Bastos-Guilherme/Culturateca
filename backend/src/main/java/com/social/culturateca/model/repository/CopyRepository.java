package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

  List<Copy> findByName(String name);
}
