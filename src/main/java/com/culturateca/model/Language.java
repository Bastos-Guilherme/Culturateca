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
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id", nullable = false, unique = true)
    private Long languageId;

    @Column(name = "language_name", nullable = false)
    private String languageName;

    @Column(name = "code_name", nullable = false)
    private String codeName;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private MasterPiece masterPiece;
}
