package com.example.dndhub.models;

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
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Value is mandatory")
    @NotBlank(message = "Value is mandatory")
    @Column(name = "tag_value")
    private String value;
    @NotNull(message = "Icon is mandatory")
    @NotBlank(message = "Icon is mandatory")
    private String iconPath;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    @NotNull(message = "Party is mandatory")
    private Party party;
}
