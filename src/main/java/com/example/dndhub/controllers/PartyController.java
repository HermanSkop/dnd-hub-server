package com.example.dndhub.controllers;

import com.example.dndhub.dtos.PartyDto;
import com.example.dndhub.services.PartyService;
import com.example.dndhub.services.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path="/party")
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

    @GetMapping(path="/{id}")
    public PartyDto getPartyById(@PathVariable("id") int id) {
        return partyService.getPartyByIdDeep(id);
    }

    @GetMapping(path="/all")
    public Iterable<PartyDto> getAllParties() {
        return partyService.getAllPartiesDeep();
    }

    @PostMapping(path="/{id}/join")
    public PartyDto joinParty(@PathVariable("id") int id) {
        return partyService.joinParty(id, playerId);
    }

    @PostMapping(path="/{id}/leave")
    public PartyDto leaveParty(@PathVariable("id") int id) {
        return partyService.leaveParty(id, playerId);
    }

    @PostMapping(path="/{id}/kick")
    public PartyDto kickPlayer(@PathVariable("id") int id, @RequestBody int playerId) {
        return partyService.leaveParty(id, playerId); // @TODO: Implement kickPlayer for different logging
    }

    @DeleteMapping(path="/{id}/delete")
    public void deleteParty(@PathVariable("id") int id) {
        partyService.deleteParty(id);
    }
}