package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Location;
import com.social.culturateca.model.repository.LocationRepository;
import com.social.culturateca.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService{
    
    @Autowired
    LocationRepository locationRepository;

    @Override
    public List<Location> locationAll(){
        try {
            return locationRepository.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Location> findByName(String name){
        try {
            return locationRepository.findByName(name);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Location findById(Long id){
        try {
            return locationRepository.findById(id).get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Location createLocation(Location location){
        try {
            if (location.getId() != null) {
                throw new RuntimeException("ID não deve ser específicado");
            }

            if (!locationRepository.findByName(location.getName()).isEmpty()) {
                throw new RuntimeException("nome da Location ja existe");
            }

            if (location.getLatitude() < -90 || location.getLatitude() > 90) {
                throw new RuntimeException("Latitude inválida");
            }

            if (location.getLongitude() < -180 || location.getLongitude() > 180) {
                throw new RuntimeException("Longitude inválida");
            }

            if (location.getLocation() != null) {

                Long locationId = location.getLocation().getId();

                Location parent = locationRepository.findById(locationId)
                    .orElseThrow(() -> new RuntimeException("Location não encontrada"));

                location.setLocation(parent);
            }

            return locationRepository.save(location);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Location editLocation(Location location){
        try {
            if (location.getId() == null) {
                throw new RuntimeException("ID não pode ser nulo");
            }

            if(locationRepository.findById(location.getId()).isEmpty()){
                throw new RuntimeException("Location não encontrada");
            }
                
            if (!locationRepository.findByName(location.getName()).isEmpty()) {
                throw new RuntimeException("nome da Location ja existe");
            }

            if (location.getLatitude() < -90 || location.getLatitude() > 90) {
                throw new RuntimeException("Latitude inválida");
            }

            if (location.getLongitude() < -180 || location.getLongitude() > 180) {
                throw new RuntimeException("Longitude inválida");
            }

            if (location.getLocation() != null) {

                Long locationId = location.getLocation().getId();

                Location parent = locationRepository.findById(locationId)
                    .orElseThrow(() -> new RuntimeException("Location não encontrada"));

                location.setLocation(parent);
            }

            return locationRepository.save(location);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void deleteLocation(Long id){
        try {
            locationRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    
}
