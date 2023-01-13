package com.culturateca.service;

import com.culturateca.model.Publisher;

import java.util.List;

public interface PublisherService {

    Publisher saveNew(Publisher publisher);

    Publisher findById(Long id);

    List<Publisher> findAll();

    void deleteById(long id);
}