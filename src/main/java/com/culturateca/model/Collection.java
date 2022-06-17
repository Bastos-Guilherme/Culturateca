package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Collection {

    @Id
    @NotNull
    private String collectionName;
    @ManytoMany
    private MasterPiece masterPiece;
}
