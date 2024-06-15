package com.example.dndhub.models.place;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public abstract class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;
}
