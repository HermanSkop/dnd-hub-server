package com.example.dndhub.dtos;

import lombok.*;

import java.io.Serializable;

@Getter
@Builder
public class TagDto implements Serializable {
    private int id;
    /**
     * The label of the tag
     */
    private String value;
    /**
     * The path to the icon shown next to the tag
     */
    private String iconPath;
    private PartyDto party;

    public TagDto(int id, String value, String iconPath, PartyDto party) {
        this.id = id;
        this.value = value;
        this.iconPath = iconPath;
        this.party = party;
        validate();
    }

    private void validate() {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Value is mandatory");
        }
        if (iconPath == null || iconPath.isBlank()) {
            throw new IllegalArgumentException("Icon is mandatory");
        }
        if (party == null) {
            throw new IllegalArgumentException("Party is mandatory");
        }
    }
}