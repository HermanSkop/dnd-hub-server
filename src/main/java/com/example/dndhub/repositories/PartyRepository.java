package com.example.dndhub.repositories;

import com.example.dndhub.models.Party;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PartyRepository extends JpaRepository<Party, Integer>, JpaSpecificationExecutor<Party> {
}