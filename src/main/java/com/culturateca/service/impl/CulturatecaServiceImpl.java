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

    @Override
    public Address updateAddressRelations(Long addressId, List<Long> locationIds, Long publisherId, Long studioId) {

        //todo check ids existence
        Address address = addressService.findById(addressId);
        List<Location> locations = address.getLocations();
        for (Long id:locationIds) {
            locations.add(locationService.findById(id));
        }
        address.setPublisher(publisherService.findById(publisherId));
        address.setStudio(studioService.findById(studioId));
        return addressService.save(address);
    }

    @Override
    public Language updateLanguageRelations(Long languageId, List<Long> masterPieceIds) {

        //todo check ids existence
        Language language = languageService.findById(languageId);
        List<MasterPiece> masterPieces = language.getMasterPieces();
        for (Long id:masterPieceIds) {
            masterPieces.add(masterPieceService.findById(id));
        }
        language.setMasterPieces(masterPieces);
        return languageService.save(language);
    }

    @Override
    public Location updateLocationRelations(Long locationId, Long addressId, List<Long> masterPieceIds) {

        //todo check ids existence
        Location location = locationService.findById(locationId);
        List<MasterPiece> masterPieces = location.getMasterPieces();
        for (Long id:masterPieceIds) {
            masterPieces.add(masterPieceService.findById(id));
        }
        location.setMasterPieces(masterPieces);
        return locationService.save(location);
    }

    @Override
    public MasterPiece updateMasterPieceRelations(Long masterPieceId, List<Long> authorIds, List<Long> languageIds, List<Long> categoryIds, List<Long> collectionIds, Long publisherId, Long studioId, Long locationId) {

        //todo check ids existence
        MasterPiece masterPiece = masterPieceService.findById(masterPieceId);
        List<Author> authors = masterPiece.getAuthors();
        for (Long id:authorIds) {
            authors.add(authorService.findById(id));
        }
        List<Language> languages = masterPiece.getLanguages();
        for (Long id:languageIds) {
            languages.add(languageService.findById(id));
        }
        List<Category> categories = masterPiece.getCategories();
        for (Long id:categoryIds) {
            categories.add(categoryService.findById(id));
        }
        List<Collection> collections = masterPiece.getCollections();
        for (Long id:collectionIds) {
            collections.add(collectionService.findById(id));
        }
        masterPiece.setAuthors(authors);
        masterPiece.setLanguages(languages);
        masterPiece.setCategories(categories);
        masterPiece.setCollections(collections);
        masterPiece.setPublisher(publisherService.findById(publisherId));
        masterPiece.setStudio(studioService.findById(studioId));
        masterPiece.setLocation(locationService.findById(locationId));
        return masterPieceService.save(masterPiece);
    }

    @Override
    public Publisher updatePublisherRelations(Long publisherId, Long addressId, List<Long> masterPieceIds) {

        //todo check ids existence
        Publisher publisher = publisherService.findById(publisherId);
        List<MasterPiece> masterPieces = publisher.getMasterPieces();
        for (Long id:masterPieceIds) {
            masterPieces.add(masterPieceService.findById(id));
        }
        publisher.setMasterPieces(masterPieces);
        publisher.setAddress(addressService.findById(addressId));
        return publisherService.save(publisher);
    }

    @Override
    public Studio updateStudioRelations(Long studioId, Long addressId, List<Long> masterPieceIds) {

        //todo check ids existence
        Studio studio = studioService.findById(studioId);
        List<MasterPiece> masterPieces = studio.getMasterPieces();
        for (Long id:masterPieceIds) {
            masterPieces.add(masterPieceService.findById(id));
        }
        studio.setMasterPieces(masterPieces);
        studio.setHq(addressService.findById(addressId));
        return studioService.save(studio);
    }

    @Override
    public Author updateAuthorRelations(Long authorId, List<Long> masterPieceIds) {

        //todo check ids existence
        Author author = authorService.findById(authorId);
        List<MasterPiece> masterPieces = author.getMasterPieces();
        for (Long id:masterPieceIds) {
            masterPieces.add(masterPieceService.findById(id));
        }
        author.setMasterPieces(masterPieces);
        return authorService.save(author);
    }

    @Override
    public Category updateCategoryRelations(Long categoryId, List<Long> masterPieceIds) {

        //todo check ids existence
        Category category = categoryService.findById(categoryId);
        List<MasterPiece> masterPieces = category.getMasterPieces();
        for (Long id:masterPieceIds) {
            masterPieces.add(masterPieceService.findById(id));
        }
        category.setMasterPieces(masterPieces);
        return categoryService.save(category);
    }

    @Override
    public Collection updateCollectionRelations(Long collectionId, List<Long> masterPieceIds) {

        //todo check ids existence
        Collection collection = collectionService.findById(collectionId);
        List<MasterPiece> masterPieces = collection.getMasterPieces();
        for (Long id:masterPieceIds) {
            masterPieces.add(masterPieceService.findById(id));
        }
        collection.setMasterPieces(masterPieces);
        return collectionService.save(collection);
    }
}
