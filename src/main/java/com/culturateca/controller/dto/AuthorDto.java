package com.culturateca.controller.dto;

import com.culturateca.model.Author;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class AuthorDto {

    private Long authorId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    private LocalDate birthDate;
    @NotNull
    private LocalDate deathDate;
    @NotBlank
    @NotNull
    @NotEmpty
    private String nationality;

    @JsonCreator
    public AuthorDto(
            @JsonProperty("authorId") Long authorId,
            @JsonProperty("name") String name,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("deathDate") LocalDate deathDate,
            @JsonProperty("nationality") String nationality
    ){
        this.setAuthorId(authorId);
        this.setName(name);
        this.setBirthDate(birthDate);
        this.setDeathDate(deathDate);
        this.setNationality(nationality);
    }

    public static AuthorDto toAuthorDto(Author author){
        return new AuthorDto(
                author.getAuthorId(),
                author.getName(),
                author.getBirthDate(),
                author.getDeathDate(),
                author.getNationality()
        );
    }

    public Author toAuthor(){
        return new Author(
                this.getAuthorId(),
                this.getName(),
                this.getBirthDate(),
                this.getDeathDate(),
                this.getNationality(),
                null
        );
    }
}