package com.culturateca.model.repository;

import com.culturateca.model.Address;
import com.culturateca.model.MasterPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
