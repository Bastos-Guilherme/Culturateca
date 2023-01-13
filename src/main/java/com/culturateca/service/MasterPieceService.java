package com.culturateca.service;

import com.culturateca.model.MasterPiece;

import java.util.List;

public interface MasterPieceService {

    MasterPiece saveNew(MasterPiece masterPiece);

    MasterPiece findById(Long id);

    List<MasterPiece> findAll();

    void deleteById(long id);
}