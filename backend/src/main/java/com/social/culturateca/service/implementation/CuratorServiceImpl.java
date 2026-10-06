package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Curator;
import com.social.culturateca.model.Location;
import com.social.culturateca.model.repository.CuratorRepository;
import com.social.culturateca.model.repository.LocationRepository;
import com.social.culturateca.service.CuratorService;

@Service
public class CuratorServiceImpl implements CuratorService {

    @Autowired
    CuratorRepository curatorRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Curator> curatorAll() {
        try {
            return curatorRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
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
    public Curator createCurator(Curator curator) {
        try {
            if (curator.getEmail() == null) {
                throw new RuntimeException("Email deve ser específicado");
            }

            if (curatorRepository.findByEmail(curator.getEmail()) != null) {
                throw new RuntimeException("Email já em uso por outro Curator");
            }

            if (curator.getLocation() != null) {

                if (curator.getLocation().getId() == null) {
                    throw new RuntimeException("Location deve possuir ID");
                }

                Long locationId = curator.getLocation().getId();

                Location parent = locationRepository.findById(locationId)
                        .orElseThrow(() -> new RuntimeException("Location não encontrada"));

                curator.setLocation(parent);
            }

            if (curator.getPassword() == null) {
                throw new RuntimeException("Password é necessária");
            }

            curator.setPassword(
                    passwordEncoder.encode(curator.getPassword()));

            if (curator.getName() == null) {
                throw new RuntimeException("Name é necessário");
            }

            if (curator.getIsPublic() == null) {
                curator.setIsPublic(true);
            }

            return curatorRepository.save(curator);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Curator editCurator(Curator curator) {
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

                Location parent = locationRepository.findById(locationId)
                        .orElseThrow(() -> new RuntimeException("Location não encontrada"));

                existingCurator.setLocation(parent);
            }
            else {
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
