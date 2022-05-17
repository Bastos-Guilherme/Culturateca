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
    private Adress adress;
    private MasterPiece masterPiece;
}
