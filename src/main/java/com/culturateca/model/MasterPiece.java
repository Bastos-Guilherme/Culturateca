package com.culturateca.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @Column(name = "masterPiece-Id", nullable = false, unique = true)
    private Long masterPieceId;

    @Column(name = "title", nullable = false)
    private String title;

    @JoinColumn(name = "author", nullable = false)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> author;

    @JoinColumn(name = "language", nullable = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Language> language;

    @Column(name = "release-date", nullable = false)
    private LocalDateTime releaseDate;

    @JoinColumn(name = "category", nullable = true)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> category;

    @JoinColumn(name = "collection", nullable = true)
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Collection> collection;

    @JoinColumn(name = "publisher", nullable = true)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Publisher> publisher;

    @Column(name = "pages", nullable = true)
    private Integer pages;

    @JoinColumn(name = "studio", nullable = true)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Studio> studio;

    @Column(name = "length", nullable = true)
    private Integer length;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @JoinColumn(name = "location", nullable = false)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Location> location;

    @Column(name = "isbn", nullable = true)
    private Integer isbn;

    @Column(name = "edition", nullable = true)
    private Integer edition;
}