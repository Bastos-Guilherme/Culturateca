package com.social.culturateca.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.social.culturateca.controller.DTO.ItemRegistrationRequest;
import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Category;
import com.social.culturateca.model.Collection;
import com.social.culturateca.model.Copy;
import com.social.culturateca.model.Location;
import com.social.culturateca.service.CanonicService;
import com.social.culturateca.service.CategoryService;
import com.social.culturateca.service.CollectionService;
import com.social.culturateca.service.CopyService;
import com.social.culturateca.service.CuratorService;
import com.social.culturateca.service.ItemRegistrationService;
import com.social.culturateca.service.LocationService;
import com.social.culturateca.service.PropertyService;

@Service
public class ItemRegistrationServiceImpl implements ItemRegistrationService {
    private final CanonicService canonicService;
    private final CopyService copyService;
    private final CategoryService categoryService;
    private final PropertyService propertyService;
    private final LocationService locationService;
    private final CollectionService collectionService;
    private final CuratorService curatorService;

    public ItemRegistrationServiceImpl(
            CanonicService canonicService,
            CopyService copyService,
            CategoryService categoryService,
            PropertyService propertyService,
            LocationService locationService,
            CollectionService collectionService,
            CuratorService curatorService) {

        this.canonicService = canonicService;
        this.copyService = copyService;
        this.categoryService = categoryService;
        this.propertyService = propertyService;
        this.locationService = locationService;
        this.collectionService = collectionService;
        this.curatorService = curatorService;
    }

    private Canonic findMatchingCanonic(ItemRegistrationRequest request) {
        List<Canonic> candidates = canonicService.findByCategory(
                request.getCategoryId(),
                categoryService);

        if (candidates == null) {
            throw new IllegalStateException(
                    "Não foi possível consultar as obras existentes.");
        }

        List<Canonic> matches = candidates.stream()
                .filter(Objects::nonNull)
                .filter(candidate -> candidate.getCategory() != null
                        && Objects.equals(
                                candidate.getCategory().getId(),
                                request.getCategoryId()))
                .filter(candidate -> sameName(candidate.getName(), request.getName()))
                .toList();

        if (matches.size() > 1) {
            throw new IllegalStateException(
                    "Mais de uma obra com esse nome foi encontrada.");
        }

        return matches.isEmpty() ? null : matches.get(0);
    }

    @Override
    @Transactional
    public Copy registerItem(
            ItemRegistrationRequest request,
            String curatorEmail) {

        validateRequest(request);

        Canonic canonic = findMatchingCanonic(request);

        if (canonic == null) {
            canonic = createCanonic(request);
        }

        Copy copy = new Copy();
        copy.setProperty(request.getProperty());
        copy.setCanonic(canonic);
        copy.setCategory(createCategoryReference(request.getCategoryId()));
        copy.setHiddenProperties(
                request.getHiddenProperties() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(request.getHiddenProperties()));

        if (request.getLocationId() != null) {
            Location location = new Location();
            location.setId(request.getLocationId());
            copy.setLocation(location);
        }

        Copy savedCopy = copyService.createCopy(
                copy,
                propertyService,
                canonicService,
                locationService,
                categoryService);

        if (savedCopy == null || savedCopy.getId() == null) {
            throw new IllegalStateException(
                    "Não foi possível cadastrar a cópia.");
        }

        if (request.getCollectionId() != null) {
            addCopyToCollection(
                    request.getCollectionId(),
                    savedCopy,
                    curatorEmail);
        }

        return savedCopy;
    }

    private void validateRequest(ItemRegistrationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException(
                    "Os dados do cadastro são obrigatórios.");
        }

        if (request.getCategoryId() == null) {
            throw new IllegalArgumentException(
                    "A categoria é obrigatória.");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome da obra é obrigatório.");
        }

        if (request.getProperty() == null
                || request.getProperty().isEmpty()) {
            throw new IllegalArgumentException(
                    "Informe pelo menos uma propriedade.");
        }

        if (request.getProperty().entrySet().stream().anyMatch(entry -> entry.getKey() == null
                || entry.getValue() == null
                || entry.getValue().isBlank())) {
            throw new IllegalArgumentException(
                    "As propriedades devem ter identificadores e valores válidos.");
        }

        if (request.getLocationId() != null
                && request.getLocationId() <= 0) {
            throw new IllegalArgumentException(
                    "A localização informada é inválida.");
        }

        if (request.getCollectionId() != null
                && request.getCollectionId() <= 0) {
            throw new IllegalArgumentException(
                    "A coleção informada é inválida.");
        }
    }

    private boolean sameName(String first, String second) {
        return first != null
                && second != null
                && first.strip().equalsIgnoreCase(second.strip());
    }

    /*private boolean propertiesMatch(
            Map<Long, String> existingProperties,
            Map<Long, String> requestedProperties) {
        if (existingProperties == null || existingProperties.isEmpty()) {
            return true;
        }

        return existingProperties.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .filter(entry -> requestedProperties.containsKey(entry.getKey()))
                .allMatch(entry -> Objects.equals(
                        normalize(entry.getValue()),
                        normalize(requestedProperties.get(entry.getKey()))));
    }

    private String normalize(String value) {
        return value == null ? "" : value.strip().toLowerCase();
    }*/

    private Canonic createCanonic(ItemRegistrationRequest request) {
        Canonic canonic = new Canonic();
        canonic.setName(request.getName().strip());
        canonic.setProperty(request.getProperty());
        canonic.setCategory(
                createCategoryReference(request.getCategoryId()));

        Canonic savedCanonic = canonicService.createCanonic(
                canonic,
                categoryService,
                propertyService);

        if (savedCanonic == null || savedCanonic.getId() == null) {
            throw new IllegalStateException(
                    "Não foi possível cadastrar a obra.");
        }

        return savedCanonic;
    }

    private Category createCategoryReference(Long categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        return category;
    }

    private void addCopyToCollection(
            Long collectionId,
            Copy copy,
            String curatorEmail) {

        Collection collection = collectionService.findById(collectionId);

        if (collection == null) {
            throw new IllegalArgumentException(
                    "A coleção informada não foi encontrada.");
        }

        if (collection.getCurator() == null
                || collection.getCurator().getId() == null
                || collection.getCurator().getEmail() == null
                || curatorEmail == null
                || !collection.getCurator().getEmail()
                        .equalsIgnoreCase(curatorEmail)) {

            throw new IllegalArgumentException(
                    "Você não tem permissão para adicionar itens a esta coleção.");
        }

        if (collection.getCopies() == null) {
            collection.setCopies(new ArrayList<>());
        }

        boolean alreadyAssociated = collection.getCopies().stream()
                .anyMatch(existing -> Objects.equals(
                        existing.getId(),
                        copy.getId()));

        if (!alreadyAssociated) {
            collection.getCopies().add(copy);
        }

        Collection updatedCollection = collectionService.editCollection(
                collection,
                curatorService);

        if (updatedCollection == null) {
            throw new IllegalStateException(
                    "Não foi possível associar o item à coleção.");
        }
    }
}
