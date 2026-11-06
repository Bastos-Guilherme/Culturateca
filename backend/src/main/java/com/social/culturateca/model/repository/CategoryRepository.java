package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  List<Category> findByNameContainingIgnoreCaseAndCurator_Id(String name, Long curatorId);

  List<Category> findByNameContainingIgnoreCaseAndCurator_IdAndIdNot(
      String name,
      Long curatorId,
      Long id
  );

  List<Category> findAllByCurator_Id(Long curatorId);
}
