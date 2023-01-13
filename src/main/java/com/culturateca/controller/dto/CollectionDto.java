package com.culturateca.controller.dto;

import com.culturateca.model.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CollectionDto {

    private Long collectionId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String collectionName;

    @JsonCreator
    public CollectionDto(
            @JsonProperty("collectionId") Long collectionId,
            @JsonProperty("collectionName") String collectionName
    ){
        this.setCollectionId(collectionId);
        this.setCollectionName(collectionName);
    }

    public static CollectionDto toCollectionDto(Collection collection){
        return new CollectionDto(
                collection.getCollectionId(),
                collection.getCollectionName()
        );
    }

    public Collection toCollection() {
        return new Collection(
                this.getCollectionId(),
                this.getCollectionName(),
                null
        );
    }
}