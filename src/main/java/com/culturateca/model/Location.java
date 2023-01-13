package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false, unique = true)
    private Long locationId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Address address;

    @Column(name = "room", nullable = false)
    private String room;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "shelf", nullable = false)
    private String shelf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<MasterPiece> masterpieces;
}