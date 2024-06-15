package com.example.dndhub.models.edition;

import com.example.dndhub.models.Party;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    protected String name;
    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description is mandatory")
    protected String description;
    private int releaseYear;
    @NotNull(message = "Type is mandatory")
    @Enumerated(EnumType.STRING)
    private EditionType type;

    @OneToMany(mappedBy = "edition")
    @ToString.Exclude
    protected Set<Party> parties = new HashSet<>();
}