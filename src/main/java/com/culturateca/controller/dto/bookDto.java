package com.culturateca.controller.dto;

import com.culturateca.model.MasterPiece;

public class bookDto {

    private Long id;
    private String title;
    private String authorName;

    public  bookDto(MasterPiece source){
        this.id = source.getId();
        this.title = source.getTitle();
        this.authorName = source.getAuthor().getName();
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String author) {
        this.authorName = author;
    }
}