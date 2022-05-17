package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Adress adress;
    private String room;
    private String unit;
    private String shelf;
    private MasterPiece masterPiece;
}
