package com.culturateca.controller;

import com.culturateca.controller.dto.MasterPieceDto;
import com.culturateca.model.MasterPiece;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/masterPiece/")
public class MasterPieceController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveMasterPiece(@Valid @RequestBody MasterPieceDto masterPiece){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewMasterPiece(masterPiece.toMasterPiece()).getMasterPieceId();
    }

    @GetMapping("/getById/{masterPieceId}")
    public MasterPiece findMasterPieceById(@RequestParam Long masterPieceId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findMasterPieceById(masterPieceId);
    }

    @GetMapping("/getAll")
    public List<MasterPiece> findAllMasterPiecees(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllMasterPieces();
    }

    @DeleteMapping("/delete/{masterPieceId}")
    public void deleteMasterPieceById(@RequestParam Long masterPieceId){
        culturatecaService.deleteMasterPieceById(masterPieceId);
    }

    @PutMapping("/update")
    public MasterPiece updateMasterPiece(@Valid @RequestBody MasterPiece masterPiece){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateMasterPiece(masterPiece);
    }

    @PutMapping("/add/into{masterPieceId}/authors{authorIds}/languages{languageIds}/categories{categoryIds}/collections{collectionIds}/publisher{publisherId}/studio{studioId}/location{locationId}")
    public MasterPiece updateMasterPieceRelations(@RequestParam(value = "masterPieceId", required = true) Long masterPieceId,
                                          @RequestParam(value = "authorIds", required = false) List<Long> authorIds,
                                          @RequestParam(value = "languageIds", required = false) List<Long> languageIds,
                                          @RequestParam(value = "categoryIds", required = false) List<Long> categoryIds,
                                          @RequestParam(value = "collectionIds", required = false) List<Long> collectionIds,
                                          @RequestParam(value = "publisherId", required = false) Long publisherId,
                                          @RequestParam(value = "studioId", required = false) Long studioId,
                                          @RequestParam(value = "locationId", required = false) Long locationId
    ){
        return culturatecaService.updateMasterPieceRelations(masterPieceId,authorIds,languageIds,categoryIds,collectionIds,publisherId,studioId,locationId);
    }
}