package com.example.dndhub.dtos.place;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PlaceDto implements Serializable {
    private int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;
}