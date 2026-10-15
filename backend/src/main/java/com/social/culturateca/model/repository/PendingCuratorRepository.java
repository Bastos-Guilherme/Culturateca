package com.social.culturateca.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.PendingCurator;

@Repository
public interface PendingCuratorRepository extends JpaRepository<PendingCurator, Long> {

    Optional<PendingCurator> findByEmail(String email);
}
