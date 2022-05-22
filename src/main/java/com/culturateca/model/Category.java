package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Category {

    @Id
    @NotNull
    private String categoryName;
    @ManytoMany
    private MasterPiece masterPiece;

    public Category() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public MasterPiece getMasterPiece() {
        return masterPiece;
    }

    public void setMasterPiece(MasterPiece masterPiece) {
        this.masterPiece = masterPiece;
    }
}
