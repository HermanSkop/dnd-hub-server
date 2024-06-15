package com.example.dndhub.dtos;

import com.example.dndhub.dtos.user.PlayerDto;
import com.example.dndhub.models.edition.Edition;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PartyDto implements Serializable {
    @Min(1)
    private static int maxPossiblePlayers = 99;

    private int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description is mandatory")
    private String description;
    @Min(1) // TODO: maxPossiblePlayers
    private int maxPlayers;
    private PartyDto.DurationDto duration;
    @NotNull(message = "Edition is mandatory")
    private Edition edition;
    @Builder.Default
    @NotNull(message = "Players saved cannot be null, use an empty set instead")
    private Set<PlayerDto> playersSaved = new HashSet<>();
    @Builder.Default
    @NotNull(message = "Participating players cannot be null, use an empty set instead")
    private Set<PlayerDto> participatingPlayers = new HashSet<>();
    @NotNull(message = "Host is mandatory")
    private PlayerDto host;
    @Builder.Default
    @NotNull(message = "Tags cannot be null, use an empty set instead")
    private Set<TagDto> tags = new HashSet<>();

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DurationDto implements Serializable {
        private int id;
        private static short maxDaysDuration = 365;
        @NotNull(message = "Starting date is mandatory")
        private LocalDate startingDate;
        private LocalDate endingDate;
    }

    @PostConstruct
    public void validateMaxPlayers() {
        if (maxPlayers > maxPossiblePlayers) {
            throw new IllegalArgumentException("Max players cannot be greater than max possible players");
        }
    }
}