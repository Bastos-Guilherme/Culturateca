package com.culturateca.service;

import com.culturateca.model.Author;

import java.util.List;

public interface AuthorService {

    Author saveNew(Author author);

    Author findById(Long id);

    List<Author> findAll();

    void deleteById(long id);
}