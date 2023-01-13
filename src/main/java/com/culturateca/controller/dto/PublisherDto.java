package com.culturateca.controller.dto;

import com.culturateca.model.Publisher;
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
public class PublisherDto {

    private Long publisherId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String publisherName;
    @NotNull
    private LocalDate foundationDate;

    @JsonCreator
    public PublisherDto(
            @JsonProperty("publisherId") Long publisherId,
            @JsonProperty("publisherName") String publisherName,
            @JsonProperty("foundationDate") LocalDate foundationDate
    ){
        this.setPublisherId(publisherId);
        this.setPublisherName(publisherName);
        this.setFoundationDate(foundationDate);
    }

    public static PublisherDto toPublisherDto(Publisher publisher){
        return new PublisherDto(
                publisher.getPublisherId(),
                publisher.getPublisherName(),
                publisher.getFoundationDate()
        );
    }

    public Publisher toPublisher() {
        return new Publisher(
                this.getPublisherId(),
                this.getPublisherName(),
                null,
                this.getFoundationDate(),
                null
        );
    }
}