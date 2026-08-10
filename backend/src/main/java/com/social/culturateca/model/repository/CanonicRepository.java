package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Category;

@Repository
public interface CanonicRepository extends JpaRepository<Canonic, Long> {
  
    List<Canonic> findByCategory(Category category);

    @Query(value = """
        select *
        from canonic
        where jsonb_exists(property, CAST(:propertyId AS text))
        """, nativeQuery = true)
    List<Canonic> findAllByProperty(@Param("propertyId") Long propertyId);
}
