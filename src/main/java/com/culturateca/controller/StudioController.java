package com.culturateca.controller;

import com.culturateca.controller.dto.StudioDto;
import com.culturateca.model.Studio;
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
@RequestMapping("/studio")
public class StudioController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveStudio(@Valid @RequestBody StudioDto studio){
        return culturatecaService.saveNewStudio(studio.toStudio()).getStudioId();
    }

    @GetMapping("/{studioId}")
    public StudioDto findStudioById(@RequestParam Long studioId){
        return StudioDto.toStudioDto(culturatecaService.findStudioById(studioId));
    }

    @GetMapping()
    public List<StudioDto> findAllStudios(){
        List<Studio> studios = culturatecaService.findAllStudios();
        List<StudioDto> studioDtos = new ArrayList<StudioDto>();
        for (Studio studio:studios) {
            studioDtos.add(StudioDto.toStudioDto(studio));
        }
        return studioDtos;
    }

    @DeleteMapping("/{studioId}")
    public void deleteStudioById(@RequestParam Long studioId){
        culturatecaService.deleteStudioById(studioId);
    }

    @PatchMapping()
    public StudioDto updateStudio(@Valid @RequestBody Studio studio){
        return StudioDto.toStudioDto(culturatecaService.updateStudio(studio));
    }

    @PutMapping("/{studioId}/masterpieces{masterPieceIds}/address{addressId}")
    public StudioDto updateStudioRelations(@RequestParam(value = "studioId", required = true) Long studioId,
                                           @RequestParam(value = "addressId", required = false) Long addressId,
                                           @RequestParam(value = "masterPieceIds", required = false) List<Long> masterPieceIds
    ){
        return StudioDto.toStudioDto(culturatecaService.updateStudioRelations(studioId,addressId,masterPieceIds));
    }
}