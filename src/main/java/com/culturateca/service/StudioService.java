package com.culturateca.service;

import com.culturateca.model.Studio;

import java.util.List;

public interface StudioService {

    Studio save(Studio studio);

    Studio findById(Long id);

    List<Studio> findAll();

    void deleteById(long id);
}