package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "collection")
public class Collection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id", nullable = false, unique = true)
    private Long collectionId;

    @Column(name = "collection_name", nullable = false)
    private String collectionName;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<MasterPiece> masterPiece;
}
