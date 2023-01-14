package com.culturateca.controller;

import com.culturateca.controller.dto.AuthorDto;
import com.culturateca.model.Author;
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
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping()
    @Headers("Content-Type: application/json")
    public Long saveAuthor(@Valid @RequestBody AuthorDto author){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewAuthor(author.toAuthor()).getAuthorId();
    }

    @GetMapping("/{authorId}")
    public AuthorDto findAuthorById(@RequestParam Long authorId){
        //todo implement checks and validations for formatting and data type
        return AuthorDto.toAuthorDto(culturatecaService.findAuthorById(authorId));
    }

    @GetMapping()
    public List<AuthorDto> findAllAuthors(){
        //todo implement checks and validations for formatting and data type
        List<Author> authors = culturatecaService.findAllAuthors();
        List<AuthorDto> authorsDtos = new ArrayList<AuthorDto>();
        for (Author author:authors) {
            authorsDtos.add(AuthorDto.toAuthorDto(author));
        }
        return authorsDtos;
    }

    @DeleteMapping("/{authorId}")
    public void deleteAuthorById(@RequestParam Long authorId){
        culturatecaService.deleteAuthorById(authorId);
    }

    @PatchMapping()
    public AuthorDto updateAuthor(@Valid @RequestBody Author author){
        //todo implement checks and validations for formatting and data type
        return AuthorDto.toAuthorDto(culturatecaService.updateAuthor(author));
    }

    @PutMapping("/{authorId}/masterpieces{masterPieceIds}")
    public AuthorDto updateAuthorRelations(@RequestParam(value = "authorId", required = true) Long authorId,
                                              @RequestParam(value = "masterPieceIds", required = true) List<Long> masterPieceIds
    ){
        return AuthorDto.toAuthorDto(culturatecaService.updateAuthorRelations(authorId,masterPieceIds));
    }
}
