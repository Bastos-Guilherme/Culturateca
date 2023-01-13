package com.culturateca.service;

import com.culturateca.model.*;

import java.util.List;

public interface CulturatecaService {

    Address saveNewAddress(Address address);

    Address findAddressById(Long addressId);
    
    List<Address> findAllAddresses();    
    
    void deleteAddressById(Long addressId);
    
    Address updateAddress(Address address);

    Author saveNewAuthor(Author author);

    Author findAuthorById(Long authorId);

    List<Author> findAllAuthors();

    void deleteAuthorById(Long authorId);

    Author updateAuthor(Author author);

    Category saveNewCategory(Category category);

    Category findCategoryById(Long categoryId);

    List<Category> findAllCategories();

    void deleteCategoryById(Long categoryId);

    Category updateCategory(Category category);

    Collection saveNewCollection(Collection collection);

    Collection findCollectionById(Long collectionId);

    List<Collection> findAllCollections();

    void deleteCollectionById(Long collectionId);

    Collection updateCollection(Collection collection);

    Language saveNewLanguage(Language language);

    Language findLanguageById(Long languageId);

    List<Language> findAllLanguages();

    void deleteLanguageById(Long languageId);

    Language updateLanguage(Language language);

    Location saveNewLocation(Location location);

    Location findLocationById(Long locationId);

    List<Location> findAllLocations();

    void deleteLocationById(Long locationId);

    Location updateLocation(Location location);

    MasterPiece saveNewMasterPiece(MasterPiece masterPiece);

    MasterPiece findMasterPieceById(Long masterPieceId);

    List<MasterPiece> findAllMasterPieces();

    void deleteMasterPieceById(Long masterPieceId);

    MasterPiece updateMasterPiece(MasterPiece masterPiece);

    Publisher saveNewPublisher(Publisher publisher);

    Publisher findPublisherById(Long publisherId);

    List<Publisher> findAllPublishers();

    void deletePublisherById(Long publisherId);

    Publisher updatePublisher(Publisher publisher);

    Studio saveNewStudio(Studio studio);

    Studio findStudioById(Long studioId);

    List<Studio> findAllStudios();

    void deleteStudioById(Long studioId);

    Studio updateStudio(Studio studio);
}