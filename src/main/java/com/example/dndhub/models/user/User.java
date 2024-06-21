package com.example.dndhub.models.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import static com.example.dndhub.configuration.AppConfig.*;

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
    /**
     * The username of the user. Must follow the usernameRegex.
     */
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    @Pattern(regexp = usernameRegex, message = "Username must be between 3 and 20 characters long and contain only letters, numbers, and the following special characters: _+&*-")
    private String username;
    /**
     * The password of the user. Must follow the passwordRegex.
     */
    @Column(length = 20)
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    @Pattern(regexp = passwordRegex, message = "Password must be between 3 and 20 characters long and contain only letters, numbers, and the following special characters: _+&*-")
    private String password;
    /**
     * The email of the user. Must be unique and follow the emailRegex.
     */
    @Column(unique = true)
    @Email(message = "Email must be a valid email", regexp = emailRegex)
    @NotNull(message = "Email is mandatory")
    @NotBlank(message = "Email is mandatory")
    private String email;
    /**
     * The date the user registered.
     */
    @NotNull(message = "Registration date is mandatory")
    private LocalDate registrationDate;

    /**
     * Validates the object before persisting it.
     */
    void validate() {
        if (registrationDate.isAfter(LocalDate.now())) {
            throw new IllegalStateException("Registration date must be in the past");
        }
    }
}
