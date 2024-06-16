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
    public PartyDto getPartyById(@PathVariable("id") int id) {
        return partyService.getPartyByIdDeep(id);
    }

    @GetMapping(path="/all")
    public Iterable<PartyDto> getAllParties() {
        return partyService.getAllPartiesDeep();
    }

}