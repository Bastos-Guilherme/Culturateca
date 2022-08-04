package com.culturateca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
public class MasterPiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String title;
    @ManyToMany
    private Author author;
    @OneToMany
    private Language language;
    private LocalDateTime releaseDate;
    @ManyToMany
    private Category category;
    @ManyToMany
    private Collection collection;
    @OneToMany
    private Publisher publisher;
    private Long pagesOrTracks;
    @OneToMany
    private Studio studio;
    private Long lenght;
    @Enumerated(Enumtype.STRING)
    private Type type;
    @OneToMany
    private Location location;
    private Long isbn;
    private Long Edition;
}
