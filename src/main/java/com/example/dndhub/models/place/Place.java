package com.example.dndhub.models.place;

import com.example.dndhub.configuration.AppConfig;
import com.example.dndhub.models.Party;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    @Size(min = AppConfig.minPlaceNameLength, max = AppConfig.maxPlaceNameLength)
    private String name;

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "place")
    private Set<Party> parties = new HashSet<>();
}
