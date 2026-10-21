package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Curator;
import com.social.culturateca.model.Location;
import com.social.culturateca.model.repository.CuratorRepository;
import com.social.culturateca.service.CuratorService;
import com.social.culturateca.service.LocationService;

@Service
public class CuratorServiceImpl implements CuratorService {

    @Autowired
    CuratorRepository curatorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Curator> findAllCurators() {
        try {
            return curatorRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void updateEmailMfaEnabled(String email, boolean enabled) {
        Curator curator = curatorRepository.findByEmail(email);

        if (curator == null) {
            throw new IllegalArgumentException("Curador não encontrado.");
        }

        curator.setEmailMfaEnabled(enabled);

        curatorRepository.save(curator);
    }

    @Override
    public Curator findById(Long id) {
        try {
            return curatorRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Curator findByEmail(String email) {
        try {
            return curatorRepository.findByEmail(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Curator> findByName(String name) {
        try {
            return curatorRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Curator createVerifiedCurator(Curator curator, LocationService locationService) {
        if (curator.getEmail() == null || curator.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "Email deve ser especificado");
        }

        if (curatorRepository.findByEmail(curator.getEmail().trim().toLowerCase()) != null) {
            throw new IllegalArgumentException(
                    "Email já em uso por outro Curator");
        }

        if (curator.getName() == null || curator.getName().isBlank()) {
            throw new IllegalArgumentException(
                    "Name é necessário");
        }

        if (curator.getPassword() == null || curator.getPassword().isBlank()) {
            throw new IllegalArgumentException(
                    "Password é necessária");
        }

        if (curator.getLocation() != null) {
            if (curator.getLocation().getId() == null) {
                throw new IllegalArgumentException(
                        "Location deve possuir ID");
            }

            Location location = locationService.findById(
                    curator.getLocation().getId());

            if (location == null) {
                throw new IllegalArgumentException(
                        "Location não encontrada");
            }

            curator.setLocation(location);
        }

        if (curator.getIsPublic() == null) {
            curator.setIsPublic(true);
        }

        return curatorRepository.save(curator);
    }

    @Override
    public Curator editCurator(Curator curator, LocationService locationService) {
        try {

            if (curator.getId() == null) {
                throw new RuntimeException("ID deve ser especificado");
            }

            Curator existingCurator = curatorRepository.findById(curator.getId())
                    .orElseThrow(() -> new RuntimeException("Curator não encontrado"));

            if (curator.getEmail() != null &&
                    !curator.getEmail().equals(existingCurator.getEmail())) {

                if (curatorRepository.findByEmail(curator.getEmail()) != null) {
                    throw new RuntimeException("Email já em uso por outro Curator");
                }

                existingCurator.setEmail(curator.getEmail());
            }

            if (curator.getLocation() != null) {

                if (curator.getLocation().getId() == null) {
                    throw new RuntimeException("Location deve possuir ID");
                }

                Long locationId = curator.getLocation().getId();

                Location parent = locationService.findById(locationId);
                if (null == parent) {
                    throw new RuntimeException("Location não encontrada");
                }

                existingCurator.setLocation(parent);
            } else {
                existingCurator.setLocation(null);
            }

            if (curator.getPassword() != null) {
                existingCurator.setPassword(passwordEncoder.encode(curator.getPassword()));
            }

            if (curator.getName() != null) {
                existingCurator.setName(curator.getName());
            }

            if (curator.getGender() != null) {
                existingCurator.setGender(curator.getGender());
            }

            if (curator.getPhone() != null) {
                existingCurator.setPhone(curator.getPhone());
            }

            if (curator.getIsPublic() != null) {
                existingCurator.setIsPublic(curator.getIsPublic());
            }

            if (curator.getBio() != null) {
                existingCurator.setBio(curator.getBio());
            }

            if (curator.getFollowing() != null) {
                existingCurator.setFollowing(curator.getFollowing());
            }

            if (curator.getProfilePicture() != null) {
                existingCurator.setProfilePicture(curator.getProfilePicture());
            }

            return curatorRepository.save(existingCurator);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteCurator(Long id) {
        try {
            curatorRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
