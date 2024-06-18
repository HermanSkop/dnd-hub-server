package com.example.dndhub.dtos;

import com.example.dndhub.models.edition.EditionType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class EditionDto implements Serializable {
    @Setter
    private int id;
    private String name;
    private String description;
    private Integer releaseYear;
    private EditionType type;
    @Builder.Default
    private Set<PartyDto> parties = new HashSet<>();

    public EditionDto(int id, String name, String description, Integer releaseYear, EditionType type, Set<PartyDto> parties) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseYear = releaseYear;
        this.type = type;
        this.parties = parties;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type is mandatory");
        }
        if (type == EditionType.CUSTOM){
            if (description == null || description.isBlank())
                throw new IllegalArgumentException("Description is mandatory");
            if (releaseYear != null)
                throw new IllegalArgumentException("Release year must be null for custom editions");
        }
        if (type == EditionType.OFFICIAL){
            if (description != null)
                throw new IllegalArgumentException("Description must be null for official editions");
            if (releaseYear == null)
                throw new IllegalArgumentException("Release year is mandatory for official editions");
        }
        if (releaseYear != null && releaseYear < 1974 && releaseYear > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Release year must be greater than 1974");
        }
        if (parties == null) {
            throw new IllegalArgumentException("Parties is mandatory");
        }
        if (parties.contains(null)) {
            throw new IllegalArgumentException("Parties cannot contain null values");
        }
    }
}