package com.culturateca.controller.dto;

import com.culturateca.model.*;
import com.culturateca.service.CulturatecaService;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class MasterPieceDto {

    private Long masterPieceId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String title;
    private List<AuthorDto> authors;
    private List<LanguageDto> languages;
    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDate releaseDate;
    private List<CategoryDto> categories;
    private List<CollectionDto> collections;
    private PublisherDto publisher;
    @NotNull
    private Integer pages;
    private StudioDto studio;
    @NotNull
    private Integer length;
    @NotBlank
    @NotNull
    @NotEmpty
    private String type;
    private LocationDto location;
    @NotNull
    private Integer isbn;
    @NotNull
    private Integer edition;

    @JsonCreator
    public MasterPieceDto(
            @JsonProperty("masterPieceId") Long masterPieceId,
            @JsonProperty("title") String title,
            @JsonProperty("authors") List<AuthorDto> authors,
            @JsonProperty("languages") List<LanguageDto> languages,
            @JsonProperty("releaseDate") LocalDate releaseDate,
            @JsonProperty("categories") List<CategoryDto> categories,
            @JsonProperty("collections") List<CollectionDto> collections,
            @JsonProperty("publishers") PublisherDto publisher,
            @JsonProperty("pages") Integer pages,
            @JsonProperty("studios") StudioDto studio,
            @JsonProperty("length") Integer length,
            @JsonProperty("type") String type,
            @JsonProperty("locations") LocationDto location,
            @JsonProperty("isbn") Integer isbn,
            @JsonProperty("edition") Integer edition
    ){
        this.setMasterPieceId(masterPieceId);
        this.setTitle(title);
        this.setAuthors(authors);
        this.setLanguages(languages);
        this.setReleaseDate(releaseDate);
        this.setCategories(categories);
        this.setCollections(collections);
        this.setPublisher(publisher);
        this.setPages(pages);
        this.setStudio(studio);
        this.setLength(length);
        this.setType(type);
        this.setLocation(location);
        this.setIsbn(isbn);
        this.setEdition(edition);
    }

    public static MasterPieceDto toMasterPieceDto(MasterPiece masterPiece){
        return new MasterPieceDto(
                masterPiece.getMasterPieceId(),
                masterPiece.getTitle(),
                null,
                null,
                masterPiece.getReleaseDate(),
                null,
                null,
                null,
                masterPiece.getPages(),
                null,
                masterPiece.getLength(),
                masterPiece.getType().value,
                null,
                masterPiece.getIsbn(),
                masterPiece.getEdition()
        );
    }

    public MasterPiece toMasterPiece(CulturatecaService culturatecaService) {
        List<Author> listAuthors = null;
        if (this.getAuthors() != null) {
            List<AuthorDto> listAuthorDto = this.getAuthors();
            for (AuthorDto a : listAuthorDto) {
                if (a.getAuthorId() == null) {
                    listAuthors.add(a.toAuthor(culturatecaService));
                } else {
                    listAuthors.add(culturatecaService.findAuthorById(a.getAuthorId()));
                }
            }
        }
        List<Language> listLanguage = null;
        if (this.getLanguages() != null) {
            List<LanguageDto> listLanguageDto = this.getLanguages();
            for (LanguageDto lgg : listLanguageDto) {
                if (lgg.getLanguageId() == null) {
                    listLanguage.add(lgg.toLanguage(culturatecaService));
                } else {
                    listLanguage.add(culturatecaService.findLanguageById(lgg.getLanguageId()));
                }
            }
        }
        List<Category> listCategories = null;
        if (this.getCategories() != null) {
            List<CategoryDto> listCategoryDto = this.getCategories();
            for (CategoryDto ctg : listCategoryDto) {
                if (ctg.getCategoryId() == null) {
                    listCategories.add(ctg.toCategory(culturatecaService));
                } else {
                    listCategories.add(culturatecaService.findCategoryById(ctg.getCategoryId()));
                }
            }
        }
        List<Collection> listCollections = null;
        if (this.getCollections() != null) {
            List<CollectionDto> listCollectionsDto = this.getCollections();
            for (CollectionDto cll : listCollectionsDto) {
                if (cll.getCollectionId() == null) {
                    listCollections.add(cll.toCollection(culturatecaService));
                } else {
                    listCollections.add(culturatecaService.findCollectionById(cll.getCollectionId()));
                }
            }
        }
        Publisher publisherFromDto;
        if (this.getPublisher() == null){
            publisherFromDto = null;
        } else if (this.getPublisher().getPublisherId() == null) {
            publisherFromDto = this.getPublisher().toPublisher(culturatecaService);
        } else {
            publisherFromDto = culturatecaService.findPublisherById(this.getPublisher().getPublisherId());
        }
        Studio studioFromDto;
        if (this.getStudio() == null){
            studioFromDto = null;
        } else if (this.getStudio().getStudioId() == null) {
            studioFromDto = this.getStudio().toStudio(culturatecaService);
        } else {
            studioFromDto = culturatecaService.findStudioById(this.getStudio().getStudioId());
        }
        Location locationFromDto;
        if (this.getLocation() == null){
            locationFromDto = null;
        } else if (this.getLocation().getLocationId() == null) {
            locationFromDto = this.getLocation().toLocation(culturatecaService);
        } else {
            locationFromDto = culturatecaService.findLocationById(this.getLocation().getLocationId());
        }
        Type typeFromDto = null;
        try {
            typeFromDto.valueOf(this.getType());
        } catch (IllegalArgumentException e) {}
        return new MasterPiece(
                this.getMasterPieceId(),
                this.getTitle(),
                listAuthors,
                listLanguage,
                this.getReleaseDate(),
                listCategories,
                listCollections,
                publisherFromDto,
                this.getPages(),
                studioFromDto,
                this.getLength(),
                typeFromDto,
                locationFromDto,
                this.getIsbn(),
                this.getEdition()
        );
    }
}