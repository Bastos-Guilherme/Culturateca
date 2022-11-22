package com.culturateca.controller.dto;

import com.culturateca.model.Author;
import com.culturateca.model.MasterPiece;
import com.culturateca.service.CulturatecaService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class AuthorDto {

    private Long authorId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String name;
    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDate birthDate;
    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDate deathDate;
    @NotBlank
    @NotNull
    @NotEmpty
    private String nationality;
    private List<MasterPieceDto> masterPieces;

    @JsonCreator
    public AuthorDto(
            @JsonProperty("authorId") Long authorId,
            @JsonProperty("name") String name,
            @JsonProperty("birthDate") LocalDate birthDate,
            @JsonProperty("deathDate") LocalDate deathDate,
            @JsonProperty("nationality") String nationality,
            @JsonProperty("masterPieces") List<MasterPieceDto> masterPieces
    ){
        this.setAuthorId(authorId);
        this.setName(name);
        this.setBirthDate(birthDate);
        this.setDeathDate(deathDate);
        this.setNationality(nationality);
        this.setMasterPieces(masterPieces);
    }

    public static AuthorDto toAuthorDto(Author author){
        return new AuthorDto(
                author.getAuthorId(),
                author.getName(),
                author.getBirthDate(),
                author.getDeathDate(),
                author.getNationality(),
                null
        );
    }

    public Author toAuthor(CulturatecaService culturatecaService){
        List<MasterPiece> listMasterPieces = null;
        if (this.getMasterPieces() != null) {
            List<MasterPieceDto> listMasterPiecesDto = this.getMasterPieces();
            for (MasterPieceDto m : listMasterPiecesDto) {
                if (m.getMasterPieceId() == null) {
                    listMasterPieces.add(m.toMasterPiece(culturatecaService));
                } else {
                    listMasterPieces.add(culturatecaService.findMasterPieceById(m.getMasterPieceId()));
                }
            }
        }
        return new Author(
                this.getAuthorId(),
                this.getName(),
                this.getBirthDate(),
                this.getDeathDate(),
                this.getNationality(),
                listMasterPieces
        );
    }
}