package com.culturateca.controller;

import com.culturateca.controller.dto.LanguageDto;
import com.culturateca.model.Language;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/language/")
public class LanguageController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveLanguage(@Valid @RequestBody LanguageDto language){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewLanguage(language.toLanguage()).getLanguageId();
    }

    @GetMapping("/getById/{languageId}")
    public Language findLanguageById(@RequestParam Long languageId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findLanguageById(languageId);
    }

    @GetMapping("/getAll")
    public List<Language> findAllLanguagees(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllLanguages();
    }

    @DeleteMapping("/delete/{languageId}")
    public void deleteLanguageById(@RequestParam Long languageId){
        culturatecaService.deleteLanguageById(languageId);
    }

    @PutMapping("/update")
    public Language updateLanguage(@Valid @RequestBody Language language){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateLanguage(language);
    }

    @PutMapping("/add/into{languageId}/masterpieces{masterPieceIds}")
    public Language updateLanguageRelations(@RequestParam(value = "languageId", required = true) Long languageId,
                                            @RequestParam(value = "masterPieceIds", required = true) List<Long> masterPieceIds
    ){
        return culturatecaService.updateLanguageRelations(languageId,masterPieceIds);
    }
}