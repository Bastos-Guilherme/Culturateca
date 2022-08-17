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
    @Column(name = "language-Id", nullable = false, unique = true)
    private Long languageId;

    @Column(name = "language-name", nullable = false)
    private String languageName;

    @Column(name = "code-name", nullable = false)
    private String codeName;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<MasterPiece> masterPiece;
}
