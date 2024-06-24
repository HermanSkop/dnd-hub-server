package com.example.dndhub.dtos;

import com.example.dndhub.configuration.AppConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class CurrentUserDto {
    @Setter
    private int id;
    private String username;
    private String email;
    private String role;

    public void setUsername(String username) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be null or blank");
        if (username.length() < AppConfig.minUsernameLength)
            throw new IllegalArgumentException("Username must be at least " + AppConfig.minUsernameLength + " characters long");
        if (username.length() > AppConfig.maxUsernameLength)
            throw new IllegalArgumentException("Username must be at most " + AppConfig.maxUsernameLength + " characters long");
        this.username = username;
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException("Email cannot be null or blank");
        if (!email.matches(AppConfig.emailRegex))
            throw new IllegalArgumentException("Email must be a valid email address");
        this.email = email;
    }

    public void setRole(String role) {
        if (role == null || role.isBlank()) throw new IllegalArgumentException("Role cannot be null or blank");
        if (!role.equals("PLAYER") && !role.equals("ADMIN"))
            throw new IllegalArgumentException("Role must be either USER or ADMIN");
        this.role = role;
    }
}
