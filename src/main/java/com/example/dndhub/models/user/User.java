package com.example.dndhub.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    private String password;
    @Column(unique = true)
    @Email
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotNull(message = "Registration date is mandatory")
    private LocalDate registrationDate;

}
