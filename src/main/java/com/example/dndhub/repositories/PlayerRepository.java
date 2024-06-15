package com.example.dndhub.repositories;

import com.example.dndhub.models.user.Player;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayerRepository extends JpaRepository<Player, Integer>, JpaSpecificationExecutor<Player> {
}