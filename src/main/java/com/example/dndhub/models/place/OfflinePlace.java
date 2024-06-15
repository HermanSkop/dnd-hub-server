package com.example.dndhub.models.place;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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
    private boolean isPublic;

    @OneToOne
    private Address address;
}
