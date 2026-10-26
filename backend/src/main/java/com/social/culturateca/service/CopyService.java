package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Copy;

public interface CopyService {
    
    public List<Copy> findAllCopies();

    public Copy findById(Long id);

    public List<Copy> findByCanonic(Long canonicId, CanonicService canonicService);

    public List<Copy> findAllByProperty(Long propertyId);

    public List<Copy> findByCanonic(Canonic canonic);

    public Copy createCopy(
            Copy copy,
            PropertyService propertyService,
            CanonicService canonicService,
            LocationService locationService,
            CategoryService categoryService
        );

    public Copy editCopy(
            Copy copy,
            PropertyService propertyService,
            CanonicService canonicService,
            LocationService locationService,
            CategoryService categoryService
        );

    public void deleteCopy(Long id);

}
