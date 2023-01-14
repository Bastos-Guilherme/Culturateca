package com.culturateca.controller;

import com.culturateca.controller.dto.PublisherDto;
import com.culturateca.model.Publisher;
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
@RequestMapping("/publisher")
public class PublisherController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long savePublisher(@Valid @RequestBody PublisherDto publisher){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewPublisher(publisher.toPublisher()).getPublisherId();
    }

    @GetMapping("/{publisherId}")
    public PublisherDto findPublisherById(@RequestParam Long publisherId){
        //todo implement checks and validations for formatting and data type
        return PublisherDto.toPublisherDto(culturatecaService.findPublisherById(publisherId));
    }

    @GetMapping()
    public List<PublisherDto> findAllPublishers(){
        //todo implement checks and validations for formatting and data type
        List<Publisher> publishers = culturatecaService.findAllPublishers();
        List<PublisherDto> publisherDtos = new ArrayList<PublisherDto>();
        for (Publisher publisher:publishers) {
            publisherDtos.add(PublisherDto.toPublisherDto(publisher));
        }
        return publisherDtos;
    }

    @DeleteMapping("/{publisherId}")
    public void deletePublisherById(@RequestParam Long publisherId){
        culturatecaService.deletePublisherById(publisherId);
    }

    @PatchMapping()
    public PublisherDto updatePublisher(@Valid @RequestBody Publisher publisher){
        //todo implement checks and validations for formatting and data type
        return PublisherDto.toPublisherDto(culturatecaService.updatePublisher(publisher));
    }

    @PutMapping("/{publisherId}/masterpieces{masterPieceIds}/address{addressId}")
    public PublisherDto updatePublisherRelations(@RequestParam(value = "publisherId", required = true) Long publisherId,
                                              @RequestParam(value = "addressId", required = false) Long addressId,
                                              @RequestParam(value = "masterPieceIds", required = false) List<Long> masterPieceIds
    ){
        return PublisherDto.toPublisherDto(culturatecaService.updatePublisherRelations(publisherId,addressId,masterPieceIds));
    }
}