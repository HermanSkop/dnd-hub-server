package com.example.dndhub.models.user;

import com.example.dndhub.models.Party;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Player extends User {
    @NotNull(message = "Avatar path is mandatory")
    @NotBlank(message = "Avatar path is mandatory")
    private String avatarPath;
    private String biography;

    @ToString.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "playersSaved")
    private Set<Party> savedParties = new HashSet<>();

    @ToString.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "participatingPlayers")
    private Set<Party> participatedParties = new HashSet<>();

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "host")
    private Set<Party> hostedParties = new HashSet<>();
}
