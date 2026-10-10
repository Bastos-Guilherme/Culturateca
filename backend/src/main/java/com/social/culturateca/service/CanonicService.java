package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Canonic;

public interface CanonicService {

    public List<Canonic> findAllCanonics();

    public Canonic findById(Long id);

    public List<Canonic> findByCategory(Long categoryId);

    public List<Canonic> findAllByProperty(Long propertyId);

    public Canonic createCanonic(Canonic canonic);

    public Canonic editCanonic(Canonic canonic);

    public void deleteCanonic(Long id);
}
