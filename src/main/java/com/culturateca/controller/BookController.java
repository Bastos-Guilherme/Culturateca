package com.culturateca.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    @RequestMapping("/all")
    public String findAll() {
        return ;
    }
}