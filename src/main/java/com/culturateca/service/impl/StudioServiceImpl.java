package com.culturateca.service.impl;

import com.culturateca.model.Studio;
import com.culturateca.model.repository.StudioRepository;
import com.culturateca.service.StudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioServiceImpl implements StudioService {

    @Autowired
    StudioRepository studioRepository;

    @Override
    public Studio findStudioById(Long id){
        Studio studio = new Studio(1L,null,"",null,null);
        return studioRepository.findById(id).orElse(studio);
    }
}
