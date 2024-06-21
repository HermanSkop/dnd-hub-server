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
    /**
     * The maximum number of platforms a player can have.
     */
    @Size(min = 1)
    private static int maxPlatformsPerPlayer;
    /**
     * The link to the platform's website.
     */
    @NotNull(message = "Link is mandatory")
    @NotBlank(message = "Link is mandatory")
    private String link;
    /**
     * The path to the icon representing the platform.
     */
    private String iconPath;
    @NotNull(message = "Type is mandatory")
    @Enumerated(EnumType.STRING)
    private OnlinePlatformType type;

    @PreUpdate
    @PrePersist
    void validate() {
        if (type == OnlinePlatformType.REGISTERED)
            if (iconPath == null || iconPath.isBlank())
                throw new IllegalStateException("iconPath must not be null or blank for registered platforms");
        if (type == OnlinePlatformType.CUSTOM){
            if (iconPath != null)
                throw new IllegalStateException("iconPath must be null for custom platforms");
        }

    }
}
