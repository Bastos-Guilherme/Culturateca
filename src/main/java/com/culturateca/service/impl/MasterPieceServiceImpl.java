package com.culturateca.service.impl;

import com.culturateca.model.MasterPiece;
import com.culturateca.model.repository.MasterPieceRepository;
import com.culturateca.service.MasterPieceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MasterPieceServiceImpl implements MasterPieceService {

    @Autowired
    MasterPieceRepository masterPieceRepository;

    @Override
    public MasterPiece findMasterPieceById(Long id){return masterPieceRepository.findById(id).get();}
}
