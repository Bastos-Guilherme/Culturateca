package com.culturateca.service;

import com.culturateca.model.Collection;

import java.util.List;

public interface CollectionService {

    Collection saveNew(Collection collection);

    Collection findById(Long id);

    List<Collection> findAll();

    void deleteById(long id);
}