package com.culturateca.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Studio {

    @Id
    @NotNull
    private String companyName;
    private LocalDateTime foundationDate;
    @ManytoOne
    private MasterPiece masterPiece;
    @OnetoOne
    private Adress hqAdress;

    public Studio() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public LocalDateTime getFoundationDate() {
        return foundationDate;
    }

    public void setFoundationDate(LocalDateTime foundationDate) {
        this.foundationDate = foundationDate;
    }

    public MasterPiece getMasterPiece() {
        return masterPiece;
    }

    public void setMasterPiece(MasterPiece masterPiece) {
        this.masterPiece = masterPiece;
    }

    public Adress getHqAdress() {
        return hqAdress;
    }

    public void setHqAdress(Adress hqAdress) {
        this.hqAdress = hqAdress;
    }
}
