package com.culturateca.controller;

import com.culturateca.controller.dto.AuthorDto;
import com.culturateca.model.Author;
import com.culturateca.service.CulturatecaService;
import feign.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/author/")
public class AuthorController {

    @Autowired
    CulturatecaService culturatecaService;

    @PostMapping("/new")
    @Headers("Content-Type: application/json")
    public Long saveAuthor(@Valid @RequestBody AuthorDto author){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.saveNewAuthor(author.toAuthor()).getAuthorId();
    }

    @GetMapping("/getById/{authorId}")
    public Author findAuthorById(@RequestParam Long authorId){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAuthorById(authorId);
    }

    @GetMapping("/getAll")
    public List<Author> findAllAuthores(){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.findAllAuthors();
    }

    @DeleteMapping("/delete/{authorId}")
    public void deleteAuthorById(@RequestParam Long authorId){
        culturatecaService.deleteAuthorById(authorId);
    }

    @PutMapping("/update")
    public Author updateAuthor(@Valid @RequestBody Author author){
        //todo implement checks and validations for formatting and data type
        return culturatecaService.updateAuthor(author);
    }

    @PutMapping("/add/into{authorId}/masterpieces{masterPieceIds}")
    public Author updateAuthorRelations(@RequestParam(value = "authorId", required = true) Long authorId,
                                              @RequestParam(value = "masterPieceIds", required = true) List<Long> masterPieceIds
    ){
        return culturatecaService.updateAuthorRelations(authorId,masterPieceIds);
    }
}
