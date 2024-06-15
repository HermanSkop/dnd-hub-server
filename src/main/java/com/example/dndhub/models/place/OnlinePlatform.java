package com.example.dndhub.models.place;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Entity
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OnlinePlatform extends Place {
    @Size(min = 1)
    private static int maxPlatformsPerPlayer = 10;
    @NotNull(message = "Link is mandatory")
    @NotBlank(message = "Link is mandatory")
    private String link;
    @NotNull(message = "Icon is mandatory")
    @NotBlank(message = "Icon is mandatory")
    private String iconPath;
    @NotNull(message = "Type is mandatory")
    @Enumerated(EnumType.STRING)
    private OnlinePlatformType type;
}
