package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class MasterPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    private Author author;
    private Language language;
    private LocalDateTime releaseDate;
    private Category category;
    private Collection collection;
    private Publisher publisher;
    private Long pagesOrTracks;
    private Studio studio;
    private Long lenght;
    private Type type;
    private Location location;
    private Long isbn;
    private Long Edition;
}
