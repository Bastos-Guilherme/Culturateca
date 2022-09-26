package com.culturateca.service.impl;

import com.culturateca.model.Collection;
import com.culturateca.model.repository.CollectionRepository;
import com.culturateca.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    @Override
    public Collection findCollectionById(Long id){return collectionRepository.findById(id).get();}
}
