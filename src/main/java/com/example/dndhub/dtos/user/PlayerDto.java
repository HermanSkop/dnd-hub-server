package com.example.dndhub.dtos.user;

import com.example.dndhub.dtos.PartyDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerDto extends UserDto implements Serializable {
    @NotNull(message = "Avatar path is mandatory")
    @NotBlank(message = "Avatar path is mandatory")
    @URL(message = "Avatar path must be a valid URL")
    private String avatarPath;
    private String biography;

    @Builder.Default
    @NotNull(message = "Saved parties cannot be null, use an empty set instead")
    private Set<PartyDto> savedParties = new HashSet<>();
    @Builder.Default
    @NotNull(message = "Participated parties cannot be null, use an empty set instead")
    private Set<PartyDto> participatedParties = new HashSet<>();
    @Builder.Default
    @NotNull(message = "Hosted parties cannot be null, use an empty set instead")
    private Set<PartyDto> hostedParties = new HashSet<>();
}