package com.culturateca.service.impl;

import com.culturateca.model.*;
import com.culturateca.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        //todo check and validations for business usage
        return addressService.findById(addressId);
    }

    @Override
    public List<Address> findAllAddresses(){
        //todo check and validations for business usage
        return addressService.findAll();
    }

    @Override
    public void deleteAddressById(Long addressId){
        //todo check and validations for business usage
        addressService.deleteById(addressId);
    }

    @Override
    public Address updateAddress(Address address){
        //todo check and validations for business usage
        return addressService.save(address);
    }

    @Override
    public Author saveNewAuthor(Author author){
        //todo check and validations for business usage
        return authorService.save(author);
    }

    @Override
    public Author findAuthorById(Long authorId){
        //todo check and validations for business usage
        return authorService.findById(authorId);
    }

    @Override
    public List<Author> findAllAuthors(){
        //todo check and validations for business usage
        return authorService.findAll();
    }

    @Override
    public void deleteAuthorById(Long authorId){
        //todo check and validations for business usage
        authorService.deleteById(authorId);
    }

    @Override
    public Author updateAuthor(Author author){
        //todo check and validations for business usage
        return authorService.save(author);
    }

    @Override
    public Category saveNewCategory(Category category){
        //todo check and validations for business usage
        return categoryService.save(category);
    }

    @Override
    public Category findCategoryById(Long categoryId){
        //todo check and validations for business usage
        return categoryService.findById(categoryId);
    }

    @Override
    public List<Category> findAllCategories(){
        //todo check and validations for business usage
        return categoryService.findAll();
    }

    @Override
    public void deleteCategoryById(Long categoryId){
        //todo check and validations for business usage
        categoryService.deleteById(categoryId);
    }

    @Override
    public Category updateCategory(Category category){
        //todo check and validations for business usage
        return categoryService.save(category);
    }

    @Override
    public Collection saveNewCollection(Collection collection){
        //todo check and validations for business usage
        return collectionService.save(collection);
    }

    @Override
    public Collection findCollectionById(Long collectionId){
        //todo check and validations for business usage
        return collectionService.findById(collectionId);
    }

    @Override
    public List<Collection> findAllCollections(){
        //todo check and validations for business usage
        return collectionService.findAll();
    }

    @Override
    public void deleteCollectionById(Long collectionId){
        //todo check and validations for business usage
        collectionService.deleteById(collectionId);
    }

    @Override
    public Collection updateCollection(Collection collection){
        //todo check and validations for business usage
        return collectionService.save(collection);
    }

    @Override
    public Language saveNewLanguage(Language language){
        //todo check and validations for business usage
        return languageService.save(language);
    }

    @Override
    public Language findLanguageById(Long languageId){
        //todo check and validations for business usage
        return languageService.findById(languageId);
    }

    @Override
    public List<Language> findAllLanguages(){
        //todo check and validations for business usage
        return languageService.findAll();
    }

    @Override
    public void deleteLanguageById(Long languageId){
        //todo check and validations for business usage
        languageService.deleteById(languageId);
    }

    @Override
    public Language updateLanguage(Language language){
        //todo check and validations for business usage
        return languageService.save(language);
    }

    @Override
    public Location saveNewLocation(Location location){
        //todo check and validations for business usage
        return locationService.save(location);
    }

    @Override
    public Location findLocationById(Long locationId){
        //todo check and validations for business usage
        return locationService.findById(locationId);
    }

    @Override
    public List<Location> findAllLocations(){
        //todo check and validations for business usage
        return locationService.findAll();
    }

    @Override
    public void deleteLocationById(Long locationId){
        //todo check and validations for business usage
        locationService.deleteById(locationId);
    }

    @Override
    public Location updateLocation(Location location){
        //todo check and validations for business usage
        return locationService.save(location);
    }

    @Override
    public MasterPiece saveNewMasterPiece(MasterPiece masterPiece){
        //todo check and validations for business usage
        return masterPieceService.save(masterPiece);
    }

    @Override
    public MasterPiece findMasterPieceById(Long masterPieceId){
        //todo check and validations for business usage
        return masterPieceService.findById(masterPieceId);
    }

    @Override
    public List<MasterPiece> findAllMasterPieces(){
        //todo check and validations for business usage
        return masterPieceService.findAll();
    }

    @Override
    public void deleteMasterPieceById(Long masterPieceId){
        //todo check and validations for business usage
        masterPieceService.deleteById(masterPieceId);
    }

    @Override
    public MasterPiece updateMasterPiece(MasterPiece masterPiece){
        //todo check and validations for business usage
        return masterPieceService.save(masterPiece);
    }

    @Override
    public Publisher saveNewPublisher(Publisher publisher){
        //todo check and validations for business usage
        return publisherService.save(publisher);
    }

    @Override
    public Publisher findPublisherById(Long publisherId){
        //todo check and validations for business usage
        return publisherService.findById(publisherId);
    }

    @Override
    public List<Publisher> findAllPublishers(){
        //todo check and validations for business usage
        return publisherService.findAll();
    }

    @Override
    public void deletePublisherById(Long publisherId){
        //todo check and validations for business usage
        publisherService.deleteById(publisherId);
    }

    @Override
    public Publisher updatePublisher(Publisher publisher){
        //todo check and validations for business usage
        return publisherService.save(publisher);
    }

    @Override
    public Studio saveNewStudio(Studio studio){
        //todo check and validations for business usage
        return studioService.save(studio);
    }

    @Override
    public Studio findStudioById(Long studioId){
        //todo check and validations for business usage
        return studioService.findById(studioId);
    }

    @Override
    public List<Studio> findAllStudios(){
        //todo check and validations for business usage
        return studioService.findAll();
    }

    @Override
    public void deleteStudioById(Long studioId){
        //todo check and validations for business usage
        studioService.deleteById(studioId);
    }

    @Override
    public Studio updateStudio(Studio studio){
        //todo check and validations for business usage
        return studioService.save(studio);
    }
}
