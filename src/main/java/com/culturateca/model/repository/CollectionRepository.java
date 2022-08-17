package com.culturateca.model.repository;

import com.culturateca.model.Collection;
import com.culturateca.model.MasterPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {
}
