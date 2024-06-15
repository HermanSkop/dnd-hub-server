package com.example.dndhub.models.place;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Country is mandatory")
    @NotBlank(message = "Country is mandatory")
    private String country;
    @NotNull(message = "Region is mandatory")
    @NotBlank(message = "Region is mandatory")
    private String region;
    @NotNull(message = "City is mandatory")
    @NotBlank(message = "City is mandatory")
    private String city;
    @NotNull(message = "Postal code is mandatory")
    @NotBlank(message = "Postal code is mandatory")
    private String postalCode;
    @NotNull(message = "Street is mandatory")
    @NotBlank(message = "Street is mandatory")
    private String street;
    @NotNull(message = "Building number is mandatory")
    @NotBlank(message = "Building number is mandatory")
    private String buildingNumber;

    @OneToOne(mappedBy = "address", cascade = CascadeType.ALL)
    private OfflinePlace offlinePlace;
}
