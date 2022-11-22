package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "studio")
public class Studio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studio_id", nullable = false, unique = true)
    private Long studioId;

    @Column(name = "foundation_date", nullable = false)
    private LocalDate foundationDate;

    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    @OneToOne(fetch = FetchType.LAZY)
    private Address hq;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MasterPiece> masterPiece;
}
