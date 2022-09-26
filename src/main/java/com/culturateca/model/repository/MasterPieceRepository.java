package com.culturateca.model.repository;

import com.culturateca.model.MasterPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterPieceRepository extends JpaRepository<MasterPiece, Long> {
}
