
package com.social.culturateca.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.PendingLoginVerification;

@Repository
public interface PendingLoginVerificationRepository extends JpaRepository<PendingLoginVerification, String> {

    Optional<PendingLoginVerification> findByEmail(String email);

    void deleteByEmail(String email);
}
