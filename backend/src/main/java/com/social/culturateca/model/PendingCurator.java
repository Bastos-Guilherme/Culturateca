
package com.social.culturateca.model;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pending_curator")
@Data
@NoArgsConstructor
public class PendingCurator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String phone;

    @Column(name = "ispublic", nullable = false)
    private Boolean isPublic = true;

    private String bio;

    @Column(name = "profilepicture")
    private String profilePicture;

    @JdbcTypeCode(SqlTypes.JSON)
    private List<Long> following;

    @ManyToOne
    @JoinColumn(name = "location")
    private Location location;

    @Column(name = "code_hash", nullable = false)
    private String codeHash;

    @Column(name = "expires_at", nullable = false)
    private Instant expiresAt;

    @Column(name = "last_sent_at", nullable = false)
    private Instant lastSentAt;

    @Column(nullable = false)
    private int attempts = 0;
}
