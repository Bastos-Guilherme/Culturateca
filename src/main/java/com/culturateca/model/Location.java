package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Location")
public class Location {

    //to-do - relacional between entitys

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location-Id", nullable = false, unique = true)
    private Long locationId;

    @Column(name = "address", nullable = false)
    private Address address;

    @Column(name = "room", nullable = false)
    private String room;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "shelf", nullable = false)
    private String shelf;

    @Column(name = "masterpiece", nullable = false)
    private MasterPiece masterpiece;
}