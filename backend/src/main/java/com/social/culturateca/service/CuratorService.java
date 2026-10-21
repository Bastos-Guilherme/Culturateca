package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Curator;

public interface CuratorService {
    
    public List<Curator> findAllCurators();

    public void updateEmailMfaEnabled(String email, boolean enabled);

    public Curator findById(Long id);

    public Curator findByEmail(String email);

    public List<Curator> findByName(String name);

    public Curator createVerifiedCurator(Curator curator, LocationService locationService);

    public Curator editCurator(Curator curator, LocationService locationService);
    
    public void deleteCurator(Long id);

}
