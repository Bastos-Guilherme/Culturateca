package com.culturateca.controller.dto;

import com.culturateca.model.MasterPiece;
import com.culturateca.model.Type;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Setter
@Getter
public class MasterPieceDto {

    private Long masterPieceId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String title;
    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDate releaseDate;
    @NotNull
    private Integer pages;
    @NotNull
    private Integer length;
    @NotBlank
    @NotNull
    @NotEmpty
    private String type;
    @NotNull
    private Integer isbn;
    @NotNull
    private Integer edition;

    @JsonCreator
    public MasterPieceDto(
            @JsonProperty("masterPieceId") Long masterPieceId,
            @JsonProperty("title") String title,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("pages") Integer pages,
            @JsonProperty("length") Integer length,
            @JsonProperty("type") String type,
            @JsonProperty("isbn") Integer isbn,
            @JsonProperty("edition") Integer edition
    ){
        this.setMasterPieceId(masterPieceId);
        this.setTitle(title);
        this.setReleaseDate(releaseDate);
        this.setPages(pages);
        this.setLength(length);
        this.setType(type);
        this.setIsbn(isbn);
        this.setEdition(edition);
    }

    public static MasterPieceDto toMasterPieceDto(MasterPiece masterPiece){
        return new MasterPieceDto(
                masterPiece.getMasterPieceId(),
                masterPiece.getTitle(),
                masterPiece.getReleaseDate(),
                masterPiece.getPages(),
                masterPiece.getLength(),
                masterPiece.getType().value,
                masterPiece.getIsbn(),
                masterPiece.getEdition()
        );
    }

    public MasterPiece toMasterPiece() {
        return new MasterPiece(
                this.getMasterPieceId(),
                this.getTitle(),
                this.getReleaseDate(),
                this.getPages(),
                this.getLength(),
                Type.valueOf(this.getType()),
                this.getIsbn(),
                this.getEdition(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }
}