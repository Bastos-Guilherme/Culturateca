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

    public MasterPiece() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Long getPagesOrTracks() {
        return pagesOrTracks;
    }

    public void setPagesOrTracks(Long pagesOrTracks) {
        this.pagesOrTracks = pagesOrTracks;
    }

    public Studio getStudio() {
        return studio;
    }

    public void setStudio(Studio studio) {
        this.studio = studio;
    }

    public Long getLenght() {
        return lenght;
    }

    public void setLenght(Long lenght) {
        this.lenght = lenght;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public Long getEdition() {
        return Edition;
    }

    public void setEdition(Long edition) {
        Edition = edition;
    }
}
