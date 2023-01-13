package com.culturateca.service.impl;

import com.culturateca.model.MasterPiece;
import com.culturateca.model.repository.MasterPieceRepository;
import com.culturateca.service.MasterPieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterPieceServiceImpl implements MasterPieceService {

    @Autowired
    MasterPieceRepository masterPieceRepository;

    @Override
    public MasterPiece saveNew(MasterPiece masterPiece){
        return masterPieceRepository.save(masterPiece);
    };

    @Override
    public MasterPiece findById(Long id){
        return masterPieceRepository.findById(id).get();
    }

    @Override
    public List<MasterPiece> findAll(){ return masterPieceRepository.findAll(); }

    @Override
    public void deleteById(long id){ masterPieceRepository.deleteById(id); }
}