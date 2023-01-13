package com.culturateca.controller;

import com.culturateca.controller.dto.StudioDto;
import com.culturateca.model.Studio;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/studio/")
public class StudioController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveStudio(@Valid @RequestBody StudioDto studio){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewStudio(studio.toStudio()).getStudioId();
    }

    @GetMapping("/getById/{studioId}")
    public Studio findStudioById(@RequestParam Long studioId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findStudioById(studioId);
    }

    @GetMapping("/getAll")
    public List<Studio> findAllStudioes(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllStudios();
    }

    @DeleteMapping("/delete/{studioId}")
    public void deleteStudioById(@RequestParam Long studioId){
        culturatecaService.deleteStudioById(studioId);
    }

    @PutMapping("/update")
    public Studio updateStudio(@Valid @RequestBody Studio studio){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateStudio(studio);
    }
}