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
    @Column(name = "masterpiece_id", nullable = false, unique = true)
    private Long masterPieceId;

    @Column(name = "title", nullable = false)
    private String title;

    @JoinColumn(name = "author")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Author> author;

    @JoinColumn(name = "language")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Language> language;

    @Column(name = "release_date", nullable = false)
    private LocalDateTime releaseDate;

    @JoinColumn(name = "category")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Category> category;

    @JoinColumn(name = "collection")
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Collection> collection;

    @JoinColumn(name = "publisher")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Publisher> publisher;

    @Column(name = "pages")
    private Integer pages;

    @JoinColumn(name = "studio")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Studio> studio;

    @Column(name = "length")
    private Integer length;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @JoinColumn(name = "location")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Location> location;

    @Column(name = "isbn")
    private Integer isbn;

    @Column(name = "edition")
    private Integer edition;
}