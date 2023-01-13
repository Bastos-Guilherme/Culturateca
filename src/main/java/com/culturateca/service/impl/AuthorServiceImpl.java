package com.culturateca.service.impl;

import com.culturateca.model.Author;
import com.culturateca.model.repository.AuthorRepository;
import com.culturateca.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Author save(Author author){
        return authorRepository.save(author);
    };

    @Override
    public Author findById(Long id){
        return authorRepository.findById(id).get();
    }

    @Override
    public List<Author> findAll(){ return authorRepository.findAll(); }

    @Override
    public void deleteById(long id){ authorRepository.deleteById(id); }
}