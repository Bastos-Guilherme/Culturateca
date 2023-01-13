package com.culturateca.service.impl;

import com.culturateca.model.Collection;
import com.culturateca.model.repository.CollectionRepository;
import com.culturateca.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionRepository collectionRepository;

    @Override
    public Collection saveNew(Collection collection){
        return collectionRepository.save(collection);
    };

    @Override
    public Collection findById(Long id){
        return collectionRepository.findById(id).get();
    }

    @Override
    public List<Collection> findAll(){ return collectionRepository.findAll(); }

    @Override
    public void deleteById(long id){ collectionRepository.deleteById(id); }
}