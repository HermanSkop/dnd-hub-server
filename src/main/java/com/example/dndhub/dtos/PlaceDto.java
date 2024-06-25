package com.example.dndhub.dtos;

import com.example.dndhub.configuration.AppConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PlaceDto{
    @Setter
    int id;
    String name;

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (name.length() < AppConfig.minPlaceNameLength || name.length() > AppConfig.maxPlaceNameLength)
            throw new IllegalArgumentException("Name must be between " + AppConfig.minPlaceNameLength + " and "
                    + AppConfig.maxPlaceNameLength + " characters long");
        this.name = name;
    }
}