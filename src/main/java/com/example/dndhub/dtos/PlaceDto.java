package com.example.dndhub.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for {@link com.example.dndhub.models.place.Place}
 */
@Getter
public abstract class PlaceDto implements Serializable {
    @Setter
    int id;
    String name;
    Set<PartyDto> parties = new HashSet<>();

    public PlaceDto(int id, String name, Set<PartyDto> parties) {
        this.id = id;
        this.name = name;
        if (parties != null)
            this.parties = parties;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is mandatory");
        }
    }
}