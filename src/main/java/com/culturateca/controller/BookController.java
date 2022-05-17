package com.culturateca.controller;

import com.culturateca.model.MasterPiece;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @RequestMapping("/all")
    public List<MasterPiece> findAll() {
        return ;
    }
}