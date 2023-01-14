package com.culturateca.controller.dto;

import com.culturateca.model.Language;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LanguageDto {

    private Long languageId;
    @NotBlank
    @NotNull
    @NotEmpty
    private String languageName;
    @NotBlank
    @NotNull
    @NotEmpty
    @Pattern(regexp = "(\\w{2}-\\w{2})|(\\w{1}-\\w{2})|(\\w{3}-\\w{2})|(\\w{1})|(\\w{2})|(\\w{3})", message = "Please use ISO code formatting")
    private String codeName;

    @JsonCreator
    public LanguageDto(
            @JsonProperty("languageId")Long languageId,
            @JsonProperty("languageName")String languageName,
            @JsonProperty("codeName")String codeName
    ){
        this.setLanguageId(languageId);
        this.setLanguageName(languageName);
        this.setCodeName(codeName);
    }

    public static LanguageDto toLanguageDto(Language language){
        return new LanguageDto(
                language.getLanguageId(),
                language.getLanguageName(),
                language.getLanguageCode()
        );
    }

    public Language toLanguage() {
        return new Language(
                this.getLanguageId(),
                this.getLanguageName(),
                this.getCodeName(),
                null
        );
    }
}
