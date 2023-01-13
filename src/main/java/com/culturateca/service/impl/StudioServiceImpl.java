package com.culturateca.service.impl;

import com.culturateca.model.Studio;
import com.culturateca.model.repository.StudioRepository;
import com.culturateca.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    StudioRepository studioRepository;

    @Override
    public Studio saveNew(Studio studio){
        return studioRepository.save(studio);
    };

    @Override
    public Studio findById(Long id){
        return studioRepository.findById(id).get();
    }

    @Override
    public List<Studio> findAll(){ return studioRepository.findAll(); }

    @Override
    public void deleteById(long id){ studioRepository.deleteById(id); }
}