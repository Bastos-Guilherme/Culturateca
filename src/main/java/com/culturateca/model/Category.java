package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Category {

    @Id
    @NotNull
    private String categoryName;
    private MasterPiece masterPiece;
}
