package com.example.dndhub.dtos;

import com.example.dndhub.configuration.AppConfig;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class PlayerSecureDto {
    @Setter
    private int id;
    private String username;
    private String avatarPath;
    @Setter
    private String biography;

    public void setUsername(String username) {
        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be null or blank");
        if (username.length() < AppConfig.minUsernameLength || username.length() > AppConfig.maxUsernameLength)
            throw new IllegalArgumentException("Username must be between 3 and " + AppConfig.maxUsernameLength + " characters");
        this.username = username;
    }

    public void setAvatarPath(String avatarPath) {
        if (avatarPath == null || avatarPath.isBlank())
            throw new IllegalArgumentException("Avatar path cannot be null or blank");
        this.avatarPath = avatarPath;
    }
}
