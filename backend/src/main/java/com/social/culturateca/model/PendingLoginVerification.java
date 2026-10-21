
package com.social.culturateca.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "pending_login_verification",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_pending_login_verification_email_purpose",
        columnNames = {"email", "purpose"}
    )
)
@Data
@NoArgsConstructor
public class PendingLoginVerification {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(name = "code_hash", nullable = false)
    private String codeHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(nullable = false)
    private int attempts = 0;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private VerificationPurpose purpose = VerificationPurpose.LOGIN_MFA;
}
