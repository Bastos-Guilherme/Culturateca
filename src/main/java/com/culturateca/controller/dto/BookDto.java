package com.culturateca.controller.dto;

import com.culturateca.model.MasterPiece;

public class BookDto {

    private Long id;
    private String title;
    private String authorName;

    public BookDto(MasterPiece source){
        this.id = source.getId();
        this.title = source.getTitle();
        this.authorName = source.getAuthor().getName();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }
}