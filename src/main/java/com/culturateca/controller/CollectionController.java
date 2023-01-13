package com.culturateca.controller;

import com.culturateca.controller.dto.CollectionDto;
import com.culturateca.model.Collection;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/collection/")
public class CollectionController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveCollection(@Valid @RequestBody CollectionDto collection){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewCollection(collection.toCollection()).getCollectionId();
    }

    @GetMapping("/getById/{collectionId}")
    public Collection findCollectionById(@RequestParam Long collectionId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findCollectionById(collectionId);
    }

    @GetMapping("/getAll")
    public List<Collection> findAllCollectiones(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllCollections();
    }

    @DeleteMapping("/delete/{collectionId}")
    public void deleteCollectionById(@RequestParam Long collectionId){
        culturatecaService.deleteCollectionById(collectionId);
    }

    @PutMapping("/update")
    public Collection updateCollection(@Valid @RequestBody Collection collection){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateCollection(collection);
    }

    @PutMapping("/add/into{collectionId}/masterpieces{masterPieceIds}")
    public Collection updateCollectionRelations(@RequestParam(value = "collectionId", required = true) Long collectionId,
                                              @RequestParam(value = "masterPieceIds", required = true) List<Long> masterPieceIds
    ){
        return culturatecaService.updateCollectionRelations(collectionId,masterPieceIds);
    }
}
