package com.example.dndhub.models.edition;

import com.example.dndhub.dtos.PartyDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditionDto implements Serializable {
    private int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Description is mandatory")
    @NotBlank(message = "Description is mandatory")
    private String description;
    @Min(1974)
    private int releaseYear;
    @NotNull(message = "Type is mandatory")
    private EditionType type;
    @NotNull
    private Set<PartyDto> parties = new HashSet<>();
}