package com.culturateca.model.repository;

import com.culturateca.model.MasterPiece;
import com.culturateca.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
