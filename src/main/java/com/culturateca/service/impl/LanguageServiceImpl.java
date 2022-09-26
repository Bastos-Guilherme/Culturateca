package com.culturateca.service.impl;

import com.culturateca.model.Language;
import com.culturateca.model.repository.LanguageRepository;
import com.culturateca.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public Language findLanguageById(Long id){return languageRepository.findById(id).get();}
}
