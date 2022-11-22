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
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id", nullable = false, unique = true)
    private Long publisherId;

    @Column(name = "publisher_name", nullable = false)
    private String publisherName;

    @OneToOne(fetch = FetchType.LAZY)
    private Address address;

    @Column(name = "foundation_date", nullable = false)
    private LocalDate foundationDate;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MasterPiece> masterPiece;
}
