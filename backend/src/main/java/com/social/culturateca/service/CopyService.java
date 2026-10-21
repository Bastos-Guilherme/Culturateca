package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Copy;

public interface CopyService {
    
    public List<Copy> findAllCopies();

    public Copy findById(Long id);

    public List<Copy> findByCanonic(Long canonicId, CanonicService canonicService);

    public List<Copy> findAllByProperty(Long propertyId);

    public Copy createCopy(
            Copy copy,
            PropertyService propertyService,
            CanonicService canonicService,
            LocationService locationService);

    public Copy editCopy(
            Copy copy,
            PropertyService propertyService,
            CanonicService canonicService,
            LocationService locationService);

    public void deleteCopy(Long id);

}
