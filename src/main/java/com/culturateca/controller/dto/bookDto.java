package com.culturateca.controller.dto;

import com.culturateca.model.MasterPiece;

public class bookDto {

    private Long id;
    private String title;
    private String author;

    public  bookDto(MasterPiece source){
        this.id = ;
        this.title = ;
        this.author = ;
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
