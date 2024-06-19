package com.example.dndhub.services;


import com.example.dndhub.dtos.PlayerDto;
import com.example.dndhub.models.user.Player;
import com.example.dndhub.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Save a player to the database
     * @param playerDto the player to save
     * @return the id of the saved player
     */
    public int savePlayer(PlayerDto playerDto) {
        Player player = Player.builder()
                .username(playerDto.getUsername())
                .password(playerDto.getPassword())
                .email(playerDto.getEmail())
                .registrationDate(playerDto.getRegistrationDate())
                .avatarPath(playerDto.getAvatarPath())
                .biography(playerDto.getBiography())
                .build();

        if (playerDto.getId() != 0) {
            player.setId(playerDto.getId());
        }

        playerRepository.save(player);
        return player.getId();
    }

    /**
     * Get all players from the database
     * @return a set of all players
     */
    public Set<PlayerDto> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(player -> PlayerDto.builder()
                        .id(player.getId())
                        .username(player.getUsername())
                        .password(player.getPassword())
                        .email(player.getEmail())
                        .registrationDate(player.getRegistrationDate())
                        .avatarPath(player.getAvatarPath())
                        .biography(player.getBiography())
                        .build())
                .collect(Collectors.toSet());
    }

    /**
     * Get a player by id
     * @param id the id of the player
     * @return the player with the given id
     */
    public PlayerDto getPlayerById(int id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new RuntimeException("Player not found"));
        return PlayerDto.builder()
                .id(player.getId())
                .username(player.getUsername())
                .password(player.getPassword())
                .email(player.getEmail())
                .registrationDate(player.getRegistrationDate())
                .avatarPath(player.getAvatarPath())
                .biography(player.getBiography())
                .build();
    }

    /**
     * Convert a player entity to a player dto
     * @param player the player entity
     * @return the player dto
     */
    public static PlayerDto getPlayerDto(Player player) {
        if (player == null) {
            return null;
        }
        return PlayerDto.builder()
                .id(player.getId())
                .username(player.getUsername())
                .password(player.getPassword())
                .email(player.getEmail())
                .registrationDate(player.getRegistrationDate())
                .avatarPath(player.getAvatarPath())
                .biography(player.getBiography())
                .build();
    }
}