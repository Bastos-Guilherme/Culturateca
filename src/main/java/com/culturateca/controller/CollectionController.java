package com.culturateca.controller;

import com.culturateca.controller.dto.CollectionDto;
import com.culturateca.model.Collection;
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
@RequestMapping("/collection")
public class CollectionController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveCollection(@Valid @RequestBody CollectionDto collection){
        return culturatecaService.saveNewCollection(collection.toCollection()).getCollectionId();
    }

    @GetMapping("/{collectionId}")
    public CollectionDto findCollectionById(@RequestParam Long collectionId){
        return CollectionDto.toCollectionDto(culturatecaService.findCollectionById(collectionId));
    }

    @GetMapping()
    public List<CollectionDto> findAllCollections(){
        List<Collection> collections = culturatecaService.findAllCollections();
        List<CollectionDto> collectionDtos = new ArrayList<CollectionDto>();
        for (Collection collection:collections) {
            collectionDtos.add(CollectionDto.toCollectionDto(collection));
        }
        return collectionDtos;
    }

    @DeleteMapping("/{collectionId}")
    public void deleteCollectionById(@RequestParam Long collectionId){
        culturatecaService.deleteCollectionById(collectionId);
    }

    @PatchMapping()
    public CollectionDto updateCollection(@Valid @RequestBody Collection collection){
        return CollectionDto.toCollectionDto(culturatecaService.updateCollection(collection));
    }

    @PutMapping("/{collectionId}/masterpieces{masterPieceIds}")
    public CollectionDto updateCollectionRelations(@RequestParam(value = "collectionId", required = true) Long collectionId,
                                                   @RequestParam(value = "masterPieceIds", required = true) List<Long> masterPieceIds
    ){
        return CollectionDto.toCollectionDto(culturatecaService.updateCollectionRelations(collectionId,masterPieceIds));
    }
}
