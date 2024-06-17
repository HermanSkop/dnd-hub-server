package com.example.dndhub.dtos;

import com.example.dndhub.dtos.party.PartyDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.example.dndhub.models.Tag}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto implements Serializable {
    private int id;
    @NotNull(message = "Value is mandatory")
    @NotBlank(message = "Value is mandatory")
    private String value;
    @NotNull(message = "Icon is mandatory")
    @NotBlank(message = "Icon is mandatory")
    private String iconPath;
    @NotNull(message = "Party is mandatory")
    private PartyDto party;
}