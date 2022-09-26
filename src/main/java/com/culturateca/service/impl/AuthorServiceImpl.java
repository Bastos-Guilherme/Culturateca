package com.culturateca.service.impl;

import com.culturateca.model.Author;
import com.culturateca.model.repository.AuthorRepository;
import com.culturateca.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author findAuthorById(Long id){return authorRepository.findById(id).get();}
}
