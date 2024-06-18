package com.example.dndhub.repositories;

import com.example.dndhub.models.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
}