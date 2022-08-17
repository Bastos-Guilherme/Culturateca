package com.culturateca.model.repository;

import com.culturateca.model.MasterPiece;
import com.culturateca.model.Studio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioRepository extends JpaRepository<Studio, Long> {
}
