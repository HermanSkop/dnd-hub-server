package com.example.dndhub.controllers;

import com.example.dndhub.dtos.party.PartyDto;
import com.example.dndhub.services.PartyService;
import com.example.dndhub.services.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/party")
public class PartyController {
    private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

    private final int playerId = 1; // @TODO: Implement session handling
    private final PartyService partyService;
    private final PlayerService playerService;

    @Autowired
    public PartyController(PartyService partyService, PlayerService playerService) {
        this.partyService = partyService;
        this.playerService = playerService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<PartyDto> getPartyById(@PathVariable("id") int id) {
        try {
            PartyDto partyDto = partyService.getPartyByIdDeep(id);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<PartyDto>> getAllParties() {
        return new ResponseEntity<>(partyService.getAllPartiesDeep(), HttpStatus.OK);
    }

    @PostMapping(path = "/{id}/join")
    public ResponseEntity<PartyDto> joinParty(@PathVariable("id") int id) {
        try {
            PartyDto partyDto = partyService.joinParty(id, playerId);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/{id}/leave")
    public ResponseEntity<PartyDto> leaveParty(@PathVariable("id") int id) {
        try {
            PartyDto partyDto = partyService.leaveParty(id, playerId);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/{id}/kick")
    public ResponseEntity<PartyDto> kickPlayer(@PathVariable("id") int id, @RequestBody int playerId) {
        try {
            PartyDto partyDto = partyService.leaveParty(id, playerId);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> deleteParty(@PathVariable("id") int id) {
        try {
            partyService.deleteParty(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}