package com.culturateca.controller.dto;

import com.culturateca.model.Author;
import com.culturateca.service.CulturatecaService;
import lombok.*;

import java.util.List;

@Getter
@Setter
public class AuthorDto {

    private Long authorId;
    private String birthDate;
    private String deathDate;
    private String nationality;
    private List<MasterPieceDto> masterPiece;

    public AuthorDto(){
    }

    public AuthorDto toAuthorDto(Author author){
        return new AuthorDto();
    }

    public Author toAuthor(CulturatecaService culturatecaService){
        return new Author();
    }
}
