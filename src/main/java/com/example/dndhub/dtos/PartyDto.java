package com.example.dndhub.dtos;

import com.example.dndhub.models.edition.Edition;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
public class PartyDto implements Serializable {
    @Min(1)
    private static int maxPossiblePlayers = 99;

    private int id;
    private String name;
    private String description;
    private int maxPlayers;
    private PartyDto.DurationDto duration;
    private EditionDto edition;
    @Builder.Default
    private Set<PlayerDto> playersSaved = new HashSet<>();
    @Builder.Default
    private Set<PlayerDto> participatingPlayers = new HashSet<>();
    private PlayerDto host;
    @Builder.Default
    private Set<TagDto> tags = new HashSet<>();

    /**
     * Used by @Builder to validate the object
     */
    public PartyDto(int id, String name, String description, int maxPlayers, PartyDto.DurationDto duration,
                    EditionDto edition, Set<PlayerDto> playersSaved, Set<PlayerDto> participatingPlayers, PlayerDto host,
                    Set<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxPlayers = maxPlayers;
        this.duration = duration;
        this.edition = edition;
        this.playersSaved = playersSaved;
        this.participatingPlayers = participatingPlayers;
        this.host = host;
        this.tags = tags;
        validate();
    }

    @Getter
    @Builder
    public static class DurationDto implements Serializable {
        private int id;
        private static short maxDaysDuration = 365;
        private LocalDate startingDate;
        private LocalDate endingDate;

        /**
         * Used by @Builder to validate the object
         */
        DurationDto(int id, LocalDate startingDate, LocalDate endingDate) {
            this.id = id;
            this.startingDate = startingDate;
            this.endingDate = endingDate;
            validateDuration();
        }

        private void validateDuration() {
            if (startingDate == null) {
                throw new IllegalArgumentException("Starting date cannot be null");
            }
            if (startingDate.isAfter(endingDate)) {
                throw new IllegalArgumentException("Starting date must be before ending date");
            }
            if (startingDate.plusDays(maxDaysDuration).isBefore(endingDate)) {
                throw new IllegalArgumentException("Duration must be less than " + maxDaysDuration + " days");
            }
        }
    }

    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
        if (maxPlayers < 1) {
            throw new IllegalArgumentException("Max players must be at least 1");
        }
        if (duration == null) {
            throw new IllegalArgumentException("Duration cannot be null");
        }
        if (edition == null) {
            throw new IllegalArgumentException("Edition cannot be null");
        }
        if (host == null) {
            throw new IllegalArgumentException("Host cannot be null");
        }
        if (participatingPlayers == null) {
            throw new IllegalArgumentException("Participating players cannot be null, use an empty set instead");
        }
        if (playersSaved == null) {
            throw new IllegalArgumentException("Saved players cannot be null, use an empty set instead");
        }
        if (tags == null) {
            throw new IllegalArgumentException("Tags cannot be null, use an empty set instead");
        }
        if (maxPlayers > maxPossiblePlayers) {
            throw new IllegalArgumentException("Max players cannot exceed " + maxPossiblePlayers);
        }
        if (participatingPlayers.contains(host)) {
            throw new IllegalArgumentException("Host cannot participate their own party");
        }
        if (participatingPlayers.size() > maxPlayers) {
            throw new IllegalArgumentException("Participating players cannot exceed max players");
        }
        if (participatingPlayers.contains(null)) {
            throw new IllegalArgumentException("Participating players cannot contain null values");
        }
        if (playersSaved.contains(null)) {
            throw new IllegalArgumentException("Saved players cannot contain null values");
        }
        if (tags.contains(null)) {
            throw new IllegalArgumentException("Tags cannot contain null values");
        }
    }
}