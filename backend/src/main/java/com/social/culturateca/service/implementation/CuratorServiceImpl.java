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
public class CuratorServiceImpl implements CuratorService{
    
    @Autowired
    CuratorRepository curatorRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Curator> curatorAll(){
        try {
            return curatorRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Curator findByEmail(String email){
        try {
            return curatorRepository.findById(email).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Curator> findByName(String name){
        try {
            return curatorRepository.findByName(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Curator createCurator(Curator curator){
        try {
            if (curator.getEmail() == null) {
                throw new RuntimeException("Email deve ser específicado");
            }

            if (!curatorRepository.findById(curator.getEmail()).isEmpty()) {
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
                passwordEncoder.encode(curator.getPassword())
            );


            if (curator.getName() == null) {
                throw new RuntimeException("Name é necessário");
            }

            if (curator.getGender() == null) {
                throw new RuntimeException("Gender é necessário");
            }

            if (curator.getPhone() == null) {
                throw new RuntimeException("Gender é necessário");
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
    public Curator editCurator(Curator curator){
        try {
            if (curator.getEmail() == null) {
                throw new RuntimeException("Email deve ser específicado");
            }

            if (!curatorRepository.findById(curator.getEmail()).isEmpty()) {
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

            if (curator.getName() == null) {
                throw new RuntimeException("Name é necessário");
            }

            if (curator.getGender() == null) {
                throw new RuntimeException("Gender é necessário");
            }

            if (curator.getPhone() == null) {
                throw new RuntimeException("Gender é necessário");
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
    public void deleteCurator(String email){
        try {
            curatorRepository.deleteById(email);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
