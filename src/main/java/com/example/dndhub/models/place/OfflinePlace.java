package com.example.dndhub.models.place;

import jakarta.persistence.*;
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
public class OfflinePlace extends Place {
    @Size(min = 1)
    private static int maxPlacesPerPlayer = 20;
    /**
     * Tells if the place is public place or private apartments
     */
    private boolean isPublic;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Address address;
}
