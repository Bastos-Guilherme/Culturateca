package com.culturateca.service.impl;

import com.culturateca.model.Language;
import com.culturateca.model.repository.LanguageRepository;
import com.culturateca.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    LanguageRepository languageRepository;

    @Override
    public Language save(Language language){
        return languageRepository.save(language);
    };

    @Override
    public Language findById(Long id){
        return languageRepository.findById(id).get();
    }

    @Override
    public List<Language> findAll(){ return languageRepository.findAll(); }

    @Override
    public void deleteById(long id){ languageRepository.deleteById(id); }
}