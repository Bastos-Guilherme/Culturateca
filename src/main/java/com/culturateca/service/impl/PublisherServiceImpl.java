package com.culturateca.service.impl;

import com.culturateca.model.Publisher;
import com.culturateca.model.repository.PublisherRepository;
import com.culturateca.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    PublisherRepository publisherRepository;

    @Override
    public Publisher findPublisherById(Long id){
        Publisher publisher = new Publisher(1L,"",null,null,null);
        return publisherRepository.findById(id).orElse(publisher);
    }
}
