package com.example.dndhub.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@SuperBuilder
public class UserDto implements Serializable {
    protected int id;
    @NotNull(message = "Username is mandatory")
    @NotBlank(message = "Username is mandatory")
    protected String username;
    @NotNull(message = "Password is mandatory")
    @NotBlank(message = "Password is mandatory")
    protected String password;
    @NotNull(message = "Email is mandatory")
    @Email(message = "Email is not valid")
    @NotBlank(message = "Email is mandatory")
    protected String email;
    @NotNull(message = "Registration date is mandatory")
    protected LocalDate registrationDate;

    /**
     * Used by @Builder to validate the object
     */
    public UserDto(int id, String username, String password, String email, LocalDate registrationDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registrationDate = registrationDate;
        validate();
    }

    private void validate() {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is mandatory");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is mandatory");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is mandatory");
        }
        if (registrationDate == null) {
            throw new IllegalArgumentException("Registration date is mandatory");
        }
    }
}