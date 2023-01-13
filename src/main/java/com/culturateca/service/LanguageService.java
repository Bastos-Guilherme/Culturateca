package com.culturateca.service;

import com.culturateca.model.Language;

import java.util.List;

public interface LanguageService {

    Language saveNew(Language language);

    Language findById(Long id);

    List<Language> findAll();

    void deleteById(long id);
}