package com.social.culturateca.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social.culturateca.model.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

}
