package com.culturateca.model.repository;

import com.culturateca.model.Author;
import com.culturateca.model.MasterPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
