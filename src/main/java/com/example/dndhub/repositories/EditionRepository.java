package com.example.dndhub.repositories;

import com.example.dndhub.models.edition.Edition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditionRepository extends JpaRepository<Edition, Integer> {
}