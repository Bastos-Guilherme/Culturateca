package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Publisher {

    @Id
    @NotNull
    private String publisherName;
    private LocalDateTime foundationDate;
    @OnetoOne
    private Adress adress;
    @ManytoOne
    private MasterPiece masterPiece;

    public Publisher() {
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
