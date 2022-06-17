package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
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
public class Publisher {

    @Id
    @NotNull
    private String publisherName;
    private LocalDateTime foundationDate;
    @OnetoOne
    private Adress adress;
    @ManytoOne
    private MasterPiece masterPiece;
}
