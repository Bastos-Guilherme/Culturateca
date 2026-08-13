package com.social.culturateca.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

    List<Copy> findByCanonic(Canonic canonic);

    @Query(value = """
        select *
        from copy
        where jsonb_exists(property, CAST(:propertyId AS text))
        """, nativeQuery = true)
    List<Copy> findAllByProperty(@Param("propertyId") Long propertyId);

}
