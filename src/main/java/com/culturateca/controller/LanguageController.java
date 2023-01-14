package com.culturateca.controller;

import com.culturateca.controller.dto.LanguageDto;
import com.culturateca.model.Language;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Validated
@RequestMapping("/language")
public class LanguageController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveLanguage(@Valid @RequestBody LanguageDto language){
        return culturatecaService.saveNewLanguage(language.toLanguage()).getLanguageId();
    }

    @GetMapping("/{languageId}")
    public LanguageDto findLanguageById(@RequestParam Long languageId){
        return LanguageDto.toLanguageDto(culturatecaService.findLanguageById(languageId));
    }

    @GetMapping()
    public List<LanguageDto> findAllLanguages(){
        List<Language> languages = culturatecaService.findAllLanguages();
        List<LanguageDto> languageDtos = new ArrayList<LanguageDto>();
        for (Language language:languages) {
            languageDtos.add(LanguageDto.toLanguageDto(language));
        }
        return languageDtos;
    }

    @DeleteMapping("/{languageId}")
    public void deleteLanguageById(@RequestParam Long languageId){
        culturatecaService.deleteLanguageById(languageId);
    }

    @PatchMapping()
    public LanguageDto updateLanguage(@Valid @RequestBody Language language){
        return LanguageDto.toLanguageDto(culturatecaService.updateLanguage(language));
    }

    @PutMapping("/{languageId}/masterpieces{masterPieceIds}")
    public LanguageDto updateLanguageRelations(@RequestParam(value = "languageId", required = true) Long languageId,
                                            @RequestParam(value = "masterPieceIds", required = true) List<Long> masterPieceIds
    ){
        return LanguageDto.toLanguageDto(culturatecaService.updateLanguageRelations(languageId,masterPieceIds));
    }
}