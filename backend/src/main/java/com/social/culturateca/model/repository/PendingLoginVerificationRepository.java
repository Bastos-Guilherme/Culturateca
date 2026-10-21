
package com.social.culturateca.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.PendingLoginVerification;
import com.social.culturateca.model.VerificationPurpose;

@Repository
public interface PendingLoginVerificationRepository extends JpaRepository<PendingLoginVerification, String> {

    Optional<PendingLoginVerification> findByEmailAndPurpose(String email, VerificationPurpose purpose);

    void deleteByEmailAndPurpose(String email, VerificationPurpose purpose);
}
