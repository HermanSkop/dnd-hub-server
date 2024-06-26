package com.example.dndhub.dtos;

import com.example.dndhub.configuration.AppConfig;
import com.example.dndhub.models.Party;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PartyDetailsDto {
    @Setter
    private int id;
    private String name;
    private String description;
    private int maxPlayers;
    private DurationDto duration;
    private PlaceDto place;
    private PlayerSecureDto host;
    private HashSet<TagDto> tags = new HashSet<>();
    private HashSet<PlayerSecureDto> participatingPlayers = new HashSet<>();

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (name.length() < AppConfig.minPartyNameLength || name.length() > AppConfig.maxPartyNameLength)
            throw new IllegalArgumentException("Name must be between " + AppConfig.minPartyNameLength + " and "
                    + AppConfig.maxPartyNameLength + " characters");
        this.name = name;
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("Description cannot be null or blank");
        this.description = description;
    }

    public void setMaxPlayers(int maxPlayers) {
        if (maxPlayers < 1 || maxPlayers > Party.maxPossiblePlayers)
            throw new IllegalArgumentException("Max players must be between 1 and " + Party.maxPossiblePlayers);
        this.maxPlayers = maxPlayers;
    }

    public void setDuration(DurationDto duration) {
        if (duration == null)
            throw new IllegalArgumentException("Duration cannot be null");
        this.duration = duration;
    }

    public void setPlace(PlaceDto place) {
        if (place == null)
            throw new IllegalArgumentException("Place cannot be null");
        this.place = place;
    }

    public void setHost(PlayerSecureDto host) {
        if (host == null)
            throw new IllegalArgumentException("Host cannot be null");
        if (this.participatingPlayers != null && this.participatingPlayers.contains(host))
            throw new IllegalArgumentException("Host cannot participate their own party");
        this.host = host;
    }

    public void setParticipatingPlayers(HashSet<PlayerSecureDto> participatingPlayers) {
        this.participatingPlayers = Objects.requireNonNullElseGet(participatingPlayers, HashSet::new);
        if (participatingPlayers.contains(null))
            throw new IllegalArgumentException("Participating players cannot contain null");
        if (participatingPlayers.size()>maxPlayers)
            throw new IllegalArgumentException("Participating players cannot contain more than " + maxPlayers + " players");
        if (this.host != null && participatingPlayers.contains(this.host))
            throw new IllegalArgumentException("Host cannot participate their own party");
    }

    public HashSet<PlayerSecureDto> getParticipatingPlayers() {
        return new HashSet<>(participatingPlayers);
    }

    public void setTags(HashSet<TagDto> tags) {
        if (tags.contains(null))
            throw new IllegalArgumentException("Tags cannot contain null elements");
        this.tags = tags;
    }

    public HashSet<TagDto> getTags() {
        return new HashSet<>(tags);
    }
}
