package com.social.culturateca.service.implementation;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Copy;
import com.social.culturateca.model.Location;
import com.social.culturateca.model.Property;
import com.social.culturateca.model.repository.CopyRepository;
import com.social.culturateca.service.CanonicService;
import com.social.culturateca.service.CopyService;
import com.social.culturateca.service.LocationService;
import com.social.culturateca.service.PropertyService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    CopyRepository copyRepository;

    @Override
    public List<Copy> findAllCopies() {
        try {
            return copyRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Copy findById(Long id) {
        try {
            return copyRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Copy> findByCanonic(Long canonicId, CanonicService canonicService) {
        try {
            Canonic canonic = canonicService.findById(canonicId);
            if (null == canonic) {
                throw new RuntimeException("Canonic não encontrado");
            }
            return copyRepository.findByCanonic(canonic);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Copy> findAllByProperty(Long propertyId) {
        try {
            return copyRepository.findAllByProperty(propertyId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Copy createCopy(
            Copy copy,
            PropertyService propertyService,
            CanonicService canonicService,
            LocationService locationService) {
        try {
            if (copy.getId() != null) {
                throw new RuntimeException("Não deve possuir ID específico");
            }

            if (copy.getProperty() == null || copy.getProperty().isEmpty()) {
                throw new RuntimeException(
                        "Property é obrigatória");
            }

            Set<Long> propertyIds = copy.getProperty().keySet();

            List<Property> properties = propertyService.findAllPropertiesById(propertyIds.stream().toList());

            Set<Long> existingIds = properties.stream()
                    .map(Property::getId)
                    .collect(Collectors.toSet());

            for (Long propertyId : propertyIds) {

                if (!existingIds.contains(propertyId)) {
                    throw new RuntimeException(
                            "Property com ID " + propertyId + " não existe");
                }
            }

            if (copy.getCanonic() == null || copy.getCanonic().getId() == null) {
                throw new RuntimeException(
                        "Canonic é obrigatória");
            }

            if (null == canonicService.findById(copy.getCanonic().getId())) {
                throw new RuntimeException(
                        "ID de Canonic inexistente");
            }

            if (copy.getLocation() != null) {

                if (copy.getLocation().getId() == null) {
                    throw new RuntimeException("Location deve possuir ID");
                }

                Long locationId = copy.getLocation().getId();

                Location parent = locationService.findById(locationId);
                if (null == parent) {
                    throw new RuntimeException("Location não encontrada");
                }

                copy.setLocation(parent);
            }

            if (copy.getHiddenProperties() == null) {
                copy.setHiddenProperties(new ArrayList<>());
            }

            return copyRepository.save(copy);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Copy editCopy(
            Copy copy,
            PropertyService propertyService,
            CanonicService canonicService,
            LocationService locationService) {
        try {
            if (copy.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            Copy existingCopy = copyRepository.findById(copy.getId())
                    .orElseThrow(() -> new RuntimeException("Copy não encontrada"));

            if (copy.getCanonic() == null
                    || copy.getCanonic().getId() == null) {
                throw new RuntimeException("Canonic é obrigatória");
            }

            Canonic canonic = canonicService.findById(
                    copy.getCanonic().getId());

            if (canonic == null) {
                throw new RuntimeException("Canonic não encontrada");
            }

            if (copy.getLocation() == null
                    || copy.getLocation().getId() == null) {
                throw new RuntimeException("Location é obrigatória");
            }

            Location location = locationService.findById(
                    copy.getLocation().getId());

            if (location == null) {
                throw new RuntimeException("Location não encontrada");
            }

            Map<Long, String> properties = copy.getProperty() != null
                    ? copy.getProperty()
                    : new HashMap<>();

            if (!properties.isEmpty()) {
                Set<Long> propertyIds = properties.keySet();

                List<Property> existingProperties = propertyService.findAllPropertiesById(propertyIds.stream().toList());

                Set<Long> existingPropertyIds = existingProperties.stream()
                        .map(Property::getId)
                        .collect(Collectors.toSet());

                for (Long propertyId : propertyIds) {
                    if (!existingPropertyIds.contains(propertyId)) {
                        throw new RuntimeException(
                                "Property com ID " + propertyId + " não existe");
                    }
                }
            }

            List<Long> hiddenProperties = copy.getHiddenProperties() != null
                ? copy.getHiddenProperties()
                : new ArrayList<>();

            Set<Long> canonicPropertyIds = canonic.getProperty() != null
                ? canonic.getProperty().keySet()
                : new HashSet<>();

            hiddenProperties = hiddenProperties.stream()
                .filter(canonicPropertyIds::contains)
                .distinct()
                .toList();

            existingCopy.setName(copy.getName() != null && !copy.getName().isBlank()
                ? copy.getName().trim()
                : null);

            existingCopy.setProperty(new HashMap<>(properties));
            existingCopy.setHiddenProperties(new ArrayList<>(hiddenProperties));
            existingCopy.setCanonic(canonic);
            existingCopy.setLocation(location);

            return copyRepository.save(existingCopy);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteCopy(Long id) {
        Copy copy = copyRepository.findById(id).orElseThrow(() ->
            new RuntimeException("Cópia não encontrada"));

        copyRepository.deleteCollectionLinksByCopyId(id);

        copyRepository.delete(copy);
    }

}
