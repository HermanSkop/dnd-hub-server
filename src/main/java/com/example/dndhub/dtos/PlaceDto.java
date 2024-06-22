package com.example.dndhub.dtos;

import com.example.dndhub.configuration.AppConfig;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@NoArgsConstructor
public class PlaceDto{
    @Setter
    int id;
    String name;
    @JsonBackReference
    Set<PartyDetailsDto> parties;

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (name.length() < AppConfig.minPlaceNameLength || name.length() > AppConfig.maxPlaceNameLength)
            throw new IllegalArgumentException("Name must be between " + AppConfig.minPlaceNameLength + " and "
                    + AppConfig.maxPlaceNameLength + " characters long");
        this.name = name;
    }

    public void setParties(Set<PartyDetailsDto> parties) {
        this.parties = Objects.requireNonNullElseGet(parties, HashSet::new);
        if (parties.contains(null))
            this.parties = new HashSet<>();
    }

    public HashSet<PartyDetailsDto> getParties() {
        return new HashSet<>(parties);
    }
}