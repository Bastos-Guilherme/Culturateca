package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "studio")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio-Id", nullable = false, unique = true)
    private Long studioId;

    @Column(name = "foundation-date", nullable = false)
    private LocalDateTime foundationDate;

    @Column(name = "company-name", nullable = false, unique = true)
    private String companyName;

    @JoinColumn(name = "hq", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Address hq;

    @JoinColumn(name = "masterpiece", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MasterPiece masterPiece;
}
