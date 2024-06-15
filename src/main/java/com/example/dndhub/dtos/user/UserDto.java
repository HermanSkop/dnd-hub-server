package com.example.dndhub.dtos.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;


@Data
@Getter
@Setter
@ToString
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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
}