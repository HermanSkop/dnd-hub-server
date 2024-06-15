package com.example.dndhub.controllers;

import com.example.dndhub.dtos.PartyDto;
import com.example.dndhub.dtos.user.PlayerDto;
import com.example.dndhub.services.PartyService;
import com.example.dndhub.services.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(path="/party")
public class PartyController {
    private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

    private final PartyService partyService;
    private final PlayerService playerService;

    @Autowired
    public PartyController(PartyService partyService, PlayerService playerService) {
        this.partyService = partyService;
        this.playerService = playerService;
    }

    @GetMapping(path="/{id}")
    public @ResponseBody PartyDto getPartyById(@PathVariable("id") int id) {
        return partyService.getPartyById(id);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<PartyDto> getAllParties() {
        return partyService.getAllPartiesDeep();
    }

    @Bean
    public CommandLineRunner demo() {
        return _ -> {
            Set<PlayerDto> players = new HashSet<>();
            PlayerDto playerDto = PlayerDto.builder()
                    .username("John")
                    .password("123")
                    .email("email@gmail.com")
                    .registrationDate(LocalDate.now())
                    .avatarPath("path")
                    .biography("biography")
                    .build();
            PlayerDto playerDto2 = PlayerDto.builder()
                    .username("John2")
                    .password("123")
                    .email("email1@gmail.com")
                    .registrationDate(LocalDate.now())
                    .avatarPath("path")
                    .biography("biography")
                    .build();

            playerDto.setId(playerService.savePlayer(playerDto));
            playerDto2.setId(playerService.savePlayer(playerDto2));

            players.add(playerDto);
            players.add(playerDto2);

            PartyDto partyDto = PartyDto.builder()
                    .name("Party")
                    .description("Description")
                    .maxPlayers(5)
                    .duration(PartyDto.DurationDto.builder()
                            .startingDate(LocalDate.now())
                            .endingDate(LocalDate.now())
                            .build())
                    .participatingPlayers(players)
                    .host(playerDto)
                    .playersSaved(players)
                    .build();

            partyService.saveParty(partyDto);
        };
    }
}