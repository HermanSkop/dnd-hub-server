package com.example.dndhub.models.user;

import jakarta.persistence.Entity;
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
public class Admin extends User{
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "Surname is mandatory")
    @NotBlank(message = "Surname is mandatory")
    private String surname;
}
