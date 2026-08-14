package com.social.culturateca.service;

import java.util.List;

import com.social.culturateca.model.Curator;

public interface CuratorService {
    
    public List<Curator> curatorAll();

    public Curator findByEmail(String email);

    public List<Curator> findByName(String name);

    public Curator createCurator(Curator curator);

    public Curator editCurator(Curator curator);
    
    public void deleteCurator(String email);

}
