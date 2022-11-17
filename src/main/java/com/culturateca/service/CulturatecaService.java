package com.culturateca.service;

import com.culturateca.model.*;

public interface CulturatecaService {

    Address saveNewAddress(Address address);

    Address findAddressById(Long addressId);

    Author findAuthorById(Long authorId);

    Category findCategoryById(Long categoryId);

    Collection findCollectionById(Long collectionId);

    Language findLanguageById(Long languageId);

    Location findLocationById(Long locationId);

    MasterPiece findMasterPieceById(Long masterPieceId);

    Publisher findPublisherById(Long publisherId);

    Studio findStudioById(Long studioId);
}
