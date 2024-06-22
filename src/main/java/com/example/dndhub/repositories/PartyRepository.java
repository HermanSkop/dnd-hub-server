package com.example.dndhub.repositories;

import com.example.dndhub.models.Party;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface PartyRepository extends JpaRepository<Party, Integer>, JpaSpecificationExecutor<Party> {
    @EntityGraph(attributePaths = {"participatingPlayers", "place", "host"})
    Optional<Party> findById(int id);

    @EntityGraph(attributePaths = {"participatingPlayers", "place"})
    List<Party> findAll();
}