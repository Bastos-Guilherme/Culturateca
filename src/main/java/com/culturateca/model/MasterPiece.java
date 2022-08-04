package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "MasterPiece")
public class MasterPiece {

    //to-do - relacional between entitys

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masterPiece-Id", nullable = false, unique = true)
    private Long masterPieceId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private Author author;

    @Column(name = "language", nullable = false)
    private Language language;

    @Column(name = "release-date", nullable = false)
    private LocalDateTime releaseDate;

    @Column(name = "category", nullable = true)
    private Category category;

    @Column(name = "collection", nullable = true)
    private Collection collection;

    @Column(name = "publisher", nullable = true)
    private Publisher publisher;

    @Column(name = "pages", nullable = true)
    private Integer pages;

    @Column(name = "studio", nullable = true)
    private Studio studio;

    @Column(name = "length", nullable = true)
    private Integer length;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "location", nullable = false)
    private Location location;

    @Column(name = "isbn", nullable = true)
    private Integer isbn;

    @Column(name = "edition", nullable = true)
    private Integer edition;
}