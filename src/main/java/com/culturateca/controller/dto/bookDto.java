package com.culturateca.controller.dto;

public class bookDto {

    private Long id;
    private String title;
    private String author;

    public  bookDto(MasterPiece source){
        this.id = source.getId();
        this.title = source.getTitle();
        this.author = source.getAuthor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
