package com.culturateca.service.impl;

import com.culturateca.model.Publisher;
import com.culturateca.model.repository.PublisherRepository;
import com.culturateca.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    @Override
    public Publisher saveNew(Publisher publisher){
        return publisherRepository.save(publisher);
    };

    @Override
    public Publisher findById(Long id){
        return publisherRepository.findById(id).get();
    }

    @Override
    public List<Publisher> findAll(){ return publisherRepository.findAll(); }

    @Override
    public void deleteById(long id){ publisherRepository.deleteById(id); }
}