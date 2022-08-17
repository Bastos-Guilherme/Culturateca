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
@Table(name = "Location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location-Id", nullable = false, unique = true)
    private Long locationId;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Address> address;

    @Column(name = "room", nullable = false)
    private String room;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "shelf", nullable = false)
    private String shelf;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private MasterPiece masterpiece;
}