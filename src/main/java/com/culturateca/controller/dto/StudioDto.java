package com.culturateca.controller.dto;

import com.culturateca.model.Studio;
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
public class StudioDto {

    private Long studioId;
    @NotBlank
    @NotNull
    @NotEmpty
    private LocalDate foundationDate;
    @NotBlank
    @NotNull
    @NotEmpty
    private String companyName;

    @JsonCreator
    public StudioDto(
            @JsonProperty("studioId") Long studioId,
            @JsonProperty("foundationDate") LocalDate foundationDate,
            @JsonProperty("companyName") String companyName
    ){
        this.setStudioId(studioId);
        this.setFoundationDate(foundationDate);
        this.setCompanyName(companyName);
    }

    public static StudioDto toStudioDto(Studio studio){
        return new StudioDto(
                studio.getStudioId(),
                studio.getFoundationDate(),
                studio.getCompanyName()
        );
    }

    public Studio toStudio() {
        return new Studio(
                this.getStudioId(),
                this.getFoundationDate(),
                this.getCompanyName(),
                null,
                null
        );
    }
}