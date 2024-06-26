package com.example.dndhub.controllers;

import com.example.dndhub.configuration.AppConfig;
import com.example.dndhub.dtos.PartyDetailsDto;
import com.example.dndhub.dtos.PartyListItemDto;
import com.example.dndhub.services.PartyService;
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
    private final PartyService partyService;

    @Autowired
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * @param id of the party to get
     * @return the party with the given id
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<PartyDetailsDto> getPartyById(@PathVariable("id") int id) {
        try {
            PartyDetailsDto partyDto = partyService.getPartyDetailsDtoById(id);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @return all existing parties
     */
    @GetMapping(path = "/all")
    public ResponseEntity<Iterable<PartyListItemDto>> getAllParties() {
        return new ResponseEntity<>(partyService.getAllPartyDetails(), HttpStatus.OK);
    }

    /**
     * Adds current player in session to a party
     * @param id of the party to join
     */
    @PostMapping(path = "/{id}/join")
    public ResponseEntity<PartyDetailsDto> joinParty(@PathVariable("id") int id) {
        try {
            PartyDetailsDto partyDto = partyService.joinParty(id, AppConfig.testPlayerId);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Removes current player in session from a party
     * @param id of the party to leave
     */
    @PostMapping(path = "/{id}/leave")
    public ResponseEntity<PartyDetailsDto> leaveParty(@PathVariable("id") int id) {
        try {
            PartyDetailsDto partyDto = partyService.leaveParty(id, AppConfig.testPlayerId);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Kicks a player from a party
     * @param id of the party
     * @param playerId of the player to kick
     * @return the party after the player has been kicked
     */
    @PostMapping(path = "/{id}/kick")
    public ResponseEntity<PartyDetailsDto> kickPlayer(@PathVariable("id") int id, @RequestBody int playerId) {
        try {
            PartyDetailsDto partyDto = partyService.leaveParty(id, playerId);
            return new ResponseEntity<>(partyDto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a party
     * @param id of the party to delete
     * @return HTTP status code: 200 if successful, 404 if party not found
     */
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