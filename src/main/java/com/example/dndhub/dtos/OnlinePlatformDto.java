package com.example.dndhub.dtos;

import com.example.dndhub.models.place.OnlinePlatformType;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
public class OnlinePlatformDto extends PlaceDto implements Serializable {
    /**
     * The maximum number of platforms a player can have.
     */
    private static int maxPlatformsPerPlayer = 10;
    /**
     * The link to the platform's website.
     */
    private String link;
    /**
     * The path to the icon representing the platform.
     */
    private String iconPath;
    private OnlinePlatformType type;

    public OnlinePlatformDto(int id, String name, String link, String iconPath, OnlinePlatformType type, Set<PartyDto> parties) {
        super(id, name, parties);
        this.link = link;
        this.iconPath = iconPath;
        this.type = type;
        validate();
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        if (link == null || link.isBlank()) {
            throw new IllegalArgumentException("Link is mandatory");
        }
        if (iconPath == null || iconPath.isBlank()) {
            throw new IllegalArgumentException("Icon is mandatory");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type is mandatory");
        }
        if (maxPlatformsPerPlayer < 1) {
            throw new IllegalStateException("Max platforms per player must be at least 1");
        }
    }

    public static void setMaxPlatformsPerPlayer(int maxPlatformsPerPlayer) {
        if (maxPlatformsPerPlayer >= 1)
            throw new IllegalArgumentException("Max platforms per player must be at least 1");
        OnlinePlatformDto.maxPlatformsPerPlayer = maxPlatformsPerPlayer;
    }
}