package com.culturateca.controller;

import com.culturateca.controller.dto.MasterPieceDto;
import com.culturateca.model.MasterPiece;
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
@RequestMapping("/masterPiece")
public class MasterPieceController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveMasterPiece(@Valid @RequestBody MasterPieceDto masterPiece){
        return culturatecaService.saveNewMasterPiece(masterPiece.toMasterPiece()).getMasterPieceId();
    }

    @GetMapping("/{masterPieceId}")
    public MasterPieceDto findMasterPieceById(@RequestParam Long masterPieceId){
        return MasterPieceDto.toMasterPieceDto(culturatecaService.findMasterPieceById(masterPieceId));
    }

    @GetMapping()
    public List<MasterPieceDto> findAllMasterPieces(){
        List<MasterPiece> masterPieces = culturatecaService.findAllMasterPieces();
        List<MasterPieceDto> masterPieceDtos = new ArrayList<MasterPieceDto>();
        for (MasterPiece masterPiece:masterPieces) {
            masterPieceDtos.add(MasterPieceDto.toMasterPieceDto(masterPiece));
        }
        return masterPieceDtos;
    }

    @DeleteMapping("/{masterPieceId}")
    public void deleteMasterPieceById(@RequestParam Long masterPieceId){
        culturatecaService.deleteMasterPieceById(masterPieceId);
    }

    @PutMapping()
    public MasterPieceDto updateMasterPiece(@Valid @RequestBody MasterPiece masterPiece){
        return MasterPieceDto.toMasterPieceDto(culturatecaService.updateMasterPiece(masterPiece));
    }

    @PutMapping("/{masterPieceId}/authors{authorIds}/languages{languageIds}/categories{categoryIds}/collections{collectionIds}/publisher{publisherId}/studio{studioId}/location{locationId}")
    public MasterPieceDto updateMasterPieceRelations(@RequestParam(value = "masterPieceId", required = true) Long masterPieceId,
                                          @RequestParam(value = "authorIds", required = false) List<Long> authorIds,
                                          @RequestParam(value = "languageIds", required = false) List<Long> languageIds,
                                          @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
                                          @RequestParam(value = "collectionIds", required = false) List<Long> collectionIds,
                                          @RequestParam(value = "publisherId", required = false) Long publisherId,
                                          @RequestParam(value = "studioId", required = false) Long studioId,
                                          @RequestParam(value = "locationId", required = false) Long locationId
    ){
        return MasterPieceDto.toMasterPieceDto(culturatecaService.updateMasterPieceRelations(masterPieceId,authorIds,languageIds,categoryIds,collectionIds,publisherId,studioId,locationId));
    }
}