package com.social.culturateca.service.implementation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Copy;
import com.social.culturateca.model.Location;
import com.social.culturateca.model.Property;
import com.social.culturateca.model.repository.CanonicRepository;
import com.social.culturateca.model.repository.CopyRepository;
import com.social.culturateca.model.repository.LocationRepository;
import com.social.culturateca.model.repository.PropertyRepository;
import com.social.culturateca.service.CopyService;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    CanonicRepository canonicRepository;

    @Autowired
    CopyRepository copyRepository;

    @Autowired
    PropertyRepository propertyRepository;

    @Autowired
    LocationRepository locationRepository;

    @Override
    public List<Copy> copyAll(){
        try {
            return copyRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Copy findById(Long id){
        try {
            return copyRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Copy> findByCanonic(Long canonicId){
        try {
            Canonic canonic = canonicRepository.findById(canonicId)
                .orElseThrow(() -> new RuntimeException("Canonic não encontrado"));
            return copyRepository.findByCanonic(canonic);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Copy> findAllByProperty(Long propertyId){
        try {
            return copyRepository.findAllByProperty(propertyId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Copy createCopy(Copy copy){
        try {
            if (copy.getId() != null) {
                throw new RuntimeException("Não deve possuir ID específico");
            }

            if (copy.getProperty() == null || copy.getProperty().isEmpty()) {
                throw new RuntimeException(
                    "Property é obrigatória"
                );
            }   

            Set<Long> propertyIds = copy.getProperty().keySet();

            List<Property> properties = propertyRepository.findAllById(propertyIds);

            Set<Long> existingIds = properties.stream()
                    .map(Property::getId)
                    .collect(Collectors.toSet());

            for (Long propertyId : propertyIds) {

                if (!existingIds.contains(propertyId)) {
                    throw new RuntimeException(
                        "Property com ID " + propertyId + " não existe"
                    );
                }
            }

            if (copy.getCanonic() == null || copy.getCanonic().getId() == null) {
                throw new RuntimeException(
                    "Canonic é obrigatória"
                );
            }

            if (!canonicRepository.existsById(copy.getCanonic().getId())) {
                throw new RuntimeException(
                    "ID de Canonic inexistente"
                );
            }

            if (copy.getLocation() != null) {

                if (copy.getLocation().getId() == null) {
                    throw new RuntimeException("Location deve possuir ID");
                }

                Long locationId = copy.getLocation().getId();

                Location parent = locationRepository.findById(locationId)
                    .orElseThrow(() -> new RuntimeException("Location não encontrada"));

                copy.setLocation(parent);
            }

            return copyRepository.save(copy);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Copy editCopy(Copy copy){
        try {
            if (copy.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            if(copyRepository.findById(copy.getId()).isEmpty()){
                throw new RuntimeException("Copy não encontrada");
            }

            if (copy.getProperty() == null || copy.getProperty().isEmpty()) {
                throw new RuntimeException(
                    "Property é obrigatória"
                );
            }   

            Set<Long> propertyIds = copy.getProperty().keySet();

            List<Property> properties = propertyRepository.findAllById(propertyIds);

            Set<Long> existingIds = properties.stream()
                    .map(Property::getId)
                    .collect(Collectors.toSet());

            for (Long propertyId : propertyIds) {

                if (!existingIds.contains(propertyId)) {
                    throw new RuntimeException(
                        "Property com ID " + propertyId + " não existe"
                    );
                }
            }

            if (copy.getCanonic() == null || copy.getCanonic().getId() == null) {
                throw new RuntimeException(
                    "Canonic é obrigatória"
                );
            }

            if (!canonicRepository.existsById(copy.getCanonic().getId())) {
                throw new RuntimeException(
                    "ID de Canonic inexistente"
                );
            }

            if (copy.getLocation() != null) {

                if (copy.getLocation().getId() == null) {
                    throw new RuntimeException("Location deve possuir ID");
                }

                Long locationId = copy.getLocation().getId();

                Location parent = locationRepository.findById(locationId)
                    .orElseThrow(() -> new RuntimeException("Location não encontrada"));

                copy.setLocation(parent);
            }

            if (copy.getCollections() != null && !copy.getCollections().isEmpty()) {
                throw new RuntimeException(
                    "Collections não devem ser informadas na criação de uma Copy"
                );
            }

            return copyRepository.save(copy);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteCopy(Long id){
        try {
            copyRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
