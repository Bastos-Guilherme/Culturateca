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
}