package com.example.dndhub.dtos;

import com.example.dndhub.configuration.AppConfig;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Builder
public class PlayerDto implements Serializable {
    @Setter
    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected LocalDate registrationDate;
    private String avatarPath;
    private String biography;

    @Builder.Default
    private Set<PartyDto> savedParties = new HashSet<>();
    @Builder.Default
    private Set<PartyDto> participatedParties = new HashSet<>();
    @Builder.Default
    private Set<PartyDto> hostedParties = new HashSet<>();

    /**
     * Used by @Builder to validate the object
     */
    public PlayerDto(int id, String username, String password, String email, LocalDate registrationDate, String avatarPath,
                     String biography, Set<PartyDto> savedParties, Set<PartyDto> participatedParties, Set<PartyDto> hostedParties) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.registrationDate = registrationDate;
        this.avatarPath = avatarPath;
        this.biography = biography;
        this.savedParties = savedParties;
        this.participatedParties = participatedParties;
        this.hostedParties = hostedParties;
        validate();
    }

    /**
     * Used by constructor to validate the object
     */
    private void validate() {
        if (avatarPath == null || avatarPath.isBlank()) {
            throw new IllegalArgumentException("Avatar path is mandatory");
        }
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username is mandatory");
        }
        if (!isUsernameValid(username)) {
            throw new IllegalArgumentException("Username must be a valid username");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password is mandatory");
        }
        if (!isPasswordValid(password)) {
            throw new IllegalArgumentException("Password must be a valid password");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is mandatory");
        }
        if (!isMailValid(email)) {
            throw new IllegalArgumentException("Email must be a valid email");
        }
        if (registrationDate == null) {
            throw new IllegalArgumentException("Registration date is mandatory");
        }
        if (participatedParties == null) {
            throw new IllegalArgumentException("Participated parties is mandatory, use an empty set if there are none");
        }
        if (hostedParties == null) {
            throw new IllegalArgumentException("Hosted parties is mandatory, use an empty set if there are none");
        }
        if (savedParties == null) {
            throw new IllegalArgumentException("Saved parties is mandatory, use an empty set if there are none");
        }
        if (participatedParties.contains(null)) {
            throw new IllegalArgumentException("Participated parties cannot contain null values");
        }
        if (hostedParties.contains(null)) {
            throw new IllegalArgumentException("Hosted parties cannot contain null values");
        }
        if (savedParties.contains(null)) {
            throw new IllegalArgumentException("Saved parties cannot contain null values");
        }
    }

    /**
     * Validates email using a regex pattern
     *
     * @param email email to validate
     * @return true if email is valid, false otherwise
     */
    private boolean isMailValid(String email) {
        String emailRegex = AppConfig.emailRegex;
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validates username using a regex pattern
     * @param username username to validate
     * @return true if username is valid, false otherwise
     */
    private boolean isUsernameValid(String username) {
        String usernameRegex = AppConfig.usernameRegex;
        Pattern pattern = Pattern.compile(usernameRegex);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    /**
     * Validates password using a regex pattern
     * @param password password to validate
     * @return true if password is valid, false otherwise
     */
    private boolean isPasswordValid(String password) {
        String passwordRegex = AppConfig.passwordRegex;
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}