package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Canonic;

public interface CanonicService {

    public List<Canonic> findAllCanonics();

    public Canonic findById(Long id);

    public List<Canonic> findByCategory(Long categoryId, CategoryService categoryService);

    public List<Canonic> findAllByProperty(Long propertyId);

    public Canonic createCanonic(Canonic canonic, CategoryService categoryService, PropertyService propertyService);

    public Canonic editCanonic(Canonic canonic, CategoryService categoryService, PropertyService propertyService);

    public void deleteCanonic(Long id);

    public void updateCanonicFromAllCopy(Long canonicId, CopyService copyService);
}
