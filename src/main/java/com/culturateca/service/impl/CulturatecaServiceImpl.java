package com.culturateca.service.impl;

import com.culturateca.model.*;
import com.culturateca.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CulturatecaServiceImpl implements CulturatecaService {

    @Autowired
    AddressService addressService;

    @Autowired
    AuthorService authorService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    LanguageService languageService;

    @Autowired
    LocationService locationService;

    @Autowired
    MasterPieceService masterPieceService;

    @Autowired
    PublisherService publisherService;

    @Autowired
    StudioService studioService;

    @Override
    public Address saveNewAddress(Address address){
        //todo check and validations for business usage
        return addressService.save(address);
    }

    @Override
    public Address findAddressById(Long addressId){
        return addressService.findById(addressId);
    }

    @Override
    public Author findAuthorById(Long authorId) {
        return authorService.findById(authorId);
    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryService.findById(categoryId);
    }

    @Override
    public Collection findCollectionById(Long collectionId) {
        return collectionService.findById(collectionId);
    }

    @Override
    public Language findLanguageById(Long languageId) {
        return languageService.findById(languageId);
    }

    @Override
    public Location findLocationById(Long locationId) {
        return locationService.findById(locationId);
    }

    @Override
    public MasterPiece findMasterPieceById(Long masterPieceId) {
        return masterPieceService.findById(masterPieceId);
    }

    @Override
    public Publisher findPublisherById(Long publisherId) {
        return publisherService.findById(publisherId);
    }

    @Override
    public Studio findStudioById(Long studioId) {
        return studioService.findById(studioId);
    }
}
