package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "masterpiece")
public class MasterPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "masterpiece_id", nullable = false, unique = true)
    private Long masterPieceId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(name = "pages")
    private Integer pages;

    @Column(name = "length")
    private Integer length;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "isbn")
    private Integer isbn;

    @Column(name = "edition")
    private Integer edition;

    @JoinColumn(name = "masterPieceAuthor")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> author;

    @JoinColumn(name = "masterPieceLanguage")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Language> language;

    @JoinColumn(name = "masterPieceCategory")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> category;

    @JoinColumn(name = "masterPieceCollection")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Collection> collection;

    @JoinColumn(name = "masterPiecePublisher")
    @OneToMany(fetch = FetchType.LAZY)
    private Publisher publisher;

    @JoinColumn(name = "masterPieceStudio")
    @OneToMany(fetch = FetchType.LAZY)
    private Studio studio;

    @JoinColumn(name = "masterPieceLocation")
    @OneToMany(fetch = FetchType.LAZY)
    private Location location;
}