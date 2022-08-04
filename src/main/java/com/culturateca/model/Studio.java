package com.culturateca.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
