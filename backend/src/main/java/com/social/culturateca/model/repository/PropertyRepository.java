package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

  List<Property> findByNameContainingIgnoreCaseAndCurator_Id(String name, Long curatorId);

  List<Property> findByNameContainingIgnoreCaseAndCurator_IdAndIdNot(
      String name,
      Long curatorId,
      Long id
  );

  List<Property> findAllByCurator_Id(Long curatorId);
}
