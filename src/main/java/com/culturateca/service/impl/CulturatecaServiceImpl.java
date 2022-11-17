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
        //todo check for uniqueness into address
        return addressService.saveNewAddress(address);
    }

    @Override
    public Address findAddressById(Long addressId){
        return addressService.findAddressById(addressId);
    }

    @Override
    public Author findAuthorById(Long authorId) {
        return authorService.findAuthorById(authorId);
    }

    @Override
    public Category findCategoryById(Long categoryId) {
        return categoryService.findCategoryById(categoryId);
    }

    @Override
    public Collection findCollectionById(Long collectionId) {
        return collectionService.findCollectionById(collectionId);
    }

    @Override
    public Language findLanguageById(Long languageId) {
        return languageService.findLanguageById(languageId);
    }

    @Override
    public Location findLocationById(Long locationId) {
        return locationService.findLocationById(locationId);
    }

    @Override
    public MasterPiece findMasterPieceById(Long masterPieceId) {
        return masterPieceService.findMasterPieceById(masterPieceId);
    }

    @Override
    public Publisher findPublisherById(Long publisherId) {
        return publisherService.findPublisherById(publisherId);
    }

    @Override
    public Studio findStudioById(Long studioId) {
        return studioService.findStudioById(studioId);
    }
}
