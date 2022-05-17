package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Collection {

    @Id
    @NotNull
    private String collectionName;
    private MasterPiece masterPiece;
}
