package com.example.dndhub.services;


import com.example.dndhub.dtos.PlayerSecureDto;
import com.example.dndhub.models.user.Player;
import com.example.dndhub.repositories.PlayerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final PartyService partyService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerService(PlayerRepository playerRepository, PartyService partyService, ModelMapper modelMapper) {
        this.playerRepository = playerRepository;
        this.partyService = partyService;
        this.modelMapper = modelMapper;
    }
}