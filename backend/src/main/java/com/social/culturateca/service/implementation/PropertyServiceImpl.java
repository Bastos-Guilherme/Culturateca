package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Curator;
import com.social.culturateca.model.Property;
import com.social.culturateca.model.repository.PropertyRepository;
import com.social.culturateca.service.PropertyService;
import com.social.culturateca.service.CuratorService;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyRepository propertyRepository;

    @Override
    public List<Property> findAllProperties() {
        try {
            return propertyRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Property> findAllPropertiesById(List<Long> ids) {
        try {
            return propertyRepository.findAllById(ids);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Property findById(Long id) {
        try {
            return propertyRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Property> findByName(String name, Long curatorId) {
        try {
            return propertyRepository.findByNameContainingIgnoreCaseAndCurator_Id(
                    name, curatorId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Property> findAllByCurator(Long curatorId) {
        return propertyRepository.findAllByCurator_Id(curatorId);
    }

    @Override
    public Property createProperty(Property property, CuratorService curatorService) {
        try {
            if (property.getId() != null) {
                throw new RuntimeException("ID não deve ser específicado");
            }

            if (!propertyRepository
                    .findByNameContainingIgnoreCaseAndCurator_Id(property.getName(), property.getCurator().getId())
                    .isEmpty()) {
                throw new RuntimeException("Property já existe para este curator");
            }

            if (property.getCurator() == null || property.getCurator().getId() == null) {
                throw new RuntimeException("Curator é obrigatório");
            }

            Curator curator = curatorService.findById(property.getCurator().getId());

            if (curator == null) {
                throw new RuntimeException("Curator não encontrado");
            }

            property.setCurator(curator);

            return propertyRepository.save(property);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Property editProperty(Property property, CuratorService curatorService) {
        try {
            if (property.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            if (propertyRepository.findById(property.getId()).isEmpty()) {
                throw new RuntimeException("Property não encontrada");
            }

            if (!propertyRepository.findByNameContainingIgnoreCaseAndCurator_IdAndIdNot(property.getName(),
                    property.getCurator().getId(), property.getId()).isEmpty()) {
                throw new RuntimeException("Property já existe para este curator");
            }

            if (property.getCurator() == null || property.getCurator().getId() == null) {
                throw new RuntimeException("Curator é obrigatório");
            }

            Curator curator = curatorService.findById(property.getCurator().getId());

            if (curator == null) {
                throw new RuntimeException("Curator não encontrado");
            }

            property.setCurator(curator);

            return propertyRepository.save(property);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteProperty(Long id) {
        try {
            propertyRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
