
package com.social.culturateca.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.culturateca.model.Canonic;
import com.social.culturateca.model.Copy;
import com.social.culturateca.service.CanonicService;
import com.social.culturateca.service.CulturatecaService;

@Service
public class CulturatecaServiceImpl implements CulturatecaService {

    @Autowired
    CanonicService canonicService;

    @Override
    public void updateCanonic(Copy copy) {
        List<Canonic> canonics = canonicService.findAllCanonics();

        for (Canonic canonic : canonics) {
            if (copy.getName() != null && copy.getName().equals(canonic.getName())) {
                canonic.setProperty(copy.getProperty());
                canonicService.editCanonic(canonic);
                return;
            }
        }
    }
}
