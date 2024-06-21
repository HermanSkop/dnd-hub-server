package com.example.dndhub.models;

import com.example.dndhub.models.edition.Edition;
import com.example.dndhub.models.place.Place;
import com.example.dndhub.models.user.Player;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Represents a group of players to play a game. Here the game properties are defined.
 */
@Entity
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Party {
    /**
     * The maximum number that can be set as the maximum number of players.
     */
    @Size(min = 1)
    private static int maxPossiblePlayers = 99;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;
    /**
     * The description of the party. Must be at most 2000 characters long.
     */
    @Column(length = 2000)
    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description is mandatory")
    private String description;
    /**
     * The maximum number of players that can join the party.
     */
    @Min(1)
    private int maxPlayers;

    /**
     * The duration of the game.
     */
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @NotNull(message = "Duration is mandatory")
    private Duration duration;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany
    @Builder.Default
    @ToString.Exclude
    @JoinTable(name = "Players_Saved_Parties")
    private Set<Player> playersSaved = new HashSet<>();

    @ManyToMany
    @Builder.Default
    @ToString.Exclude
    @JoinTable(name = "Players_Participating_Parties")
    private Set<Player> participatingPlayers = new LinkedHashSet<>();

    @NotNull(message = "Host is mandatory")
    @ManyToOne
    private Player host;

    @NotNull(message = "Edition is mandatory")
    @ManyToOne
    private Edition edition;

    /**
     * The place where the game will take place.
     */
    @NotNull(message = "Place is mandatory")
    @ManyToOne
    private Place place;

    /**
     * Validates the party before it is persisted.
     */
    @PrePersist
    @PreUpdate
    public void validate() {
        if (maxPlayers > maxPossiblePlayers) {
            throw new IllegalArgumentException("Max players cannot exceed " + maxPossiblePlayers);
        }
        if (participatingPlayers.contains(host)) {
            throw new IllegalArgumentException("Host cannot participate their own party");
        }
    }
}
