package com.culturateca.controller;

import com.culturateca.controller.dto.BookDto;
import com.culturateca.model.MasterPiece;
import com.culturateca.repository.MasterPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private MasterPieceRepository masterPieceRepository;

    @RequestMapping("/all")
    public List<BookDto> findAll() {
        List<MasterPiece> masterPieces = masterPieceRepository.findAll();
        return BookDto.toBookDto(masterPieces);
    }
}
