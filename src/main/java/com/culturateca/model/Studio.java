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
    private MasterPiece masterPiece;
    private Adress hqAdress;
}
