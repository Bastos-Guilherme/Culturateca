package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Copy;

public interface CopyService {
    
    public List<Copy> copyAll();

    public Copy findById(Long id);

    public List<Copy> findByCanonic(Long canonicId);

    public List<Copy> findAllByProperty(Long propertyId);

    public Copy createCopy(Copy copy);

    public Copy editCopy(Copy copy);

    public void deleteCopy(Long id);

}
