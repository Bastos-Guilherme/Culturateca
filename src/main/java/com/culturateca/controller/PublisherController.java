package com.culturateca.controller;

import com.culturateca.controller.dto.PublisherDto;
import com.culturateca.model.Publisher;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/publisher/")
public class PublisherController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long savePublisher(@Valid @RequestBody PublisherDto publisher){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewPublisher(publisher.toPublisher()).getPublisherId();
    }

    @GetMapping("/getById/{publisherId}")
    public Publisher findPublisherById(@RequestParam Long publisherId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findPublisherById(publisherId);
    }

    @GetMapping("/getAll")
    public List<Publisher> findAllPublisheres(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllPublishers();
    }

    @DeleteMapping("/delete/{publisherId}")
    public void deletePublisherById(@RequestParam Long publisherId){
        culturatecaService.deletePublisherById(publisherId);
    }

    @PutMapping("/update")
    public Publisher updatePublisher(@Valid @RequestBody Publisher publisher){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updatePublisher(publisher);
    }

    @PutMapping("/add/into{publisherId}/masterpieces{masterPieceIds}/address{addressId}")
    public Publisher updatePublisherRelations(@RequestParam(value = "publisherId", required = true) Long publisherId,
                                              @RequestParam(value = "addressId", required = false) Long addressId,
                                              @RequestParam(value = "masterPieceIds", required = false) List<Long> masterPieceIds
    ){
        return culturatecaService.updatePublisherRelations(publisherId,addressId,masterPieceIds);
    }
}