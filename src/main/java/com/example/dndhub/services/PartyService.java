package com.example.dndhub.services;

import com.example.dndhub.dtos.PartyDetailsDto;
import com.example.dndhub.dtos.PartyListItemDto;
import com.example.dndhub.models.Party;
import com.example.dndhub.models.user.Player;
import com.example.dndhub.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PartyService {
    private final PlayerRepository playerRepository;
    private final PartyRepository partyRepository;
    private final TagRepository tagRepository;
    private final EditionRepository editionRepository;
    private final PlaceRepository placeRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PartyService(PlayerRepository playerService, PartyRepository partyRepository, TagRepository tagRepository,
                        EditionRepository editionRepository, PlaceRepository placeRepository, ModelMapper modelMapper) {
        this.playerRepository = playerService;
        this.partyRepository = partyRepository;
        this.tagRepository = tagRepository;
        this.editionRepository = editionRepository;
        this.placeRepository = placeRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * Gets all parties from the database with associations included
     * @return a set of all parties with associations included
     */
    public Set<PartyListItemDto> getAllPartyDetails() {
        return partyRepository.findAll().stream()
                .map(party -> modelMapper.map(party, PartyListItemDto.class))
                .collect(Collectors.toSet());

    }

    /**
     * Gets a party by id from the database with associations included
     * @param id the id of the party
     * @return the party with the given id with associations included
     * @throws EntityNotFoundException if the party is not found
     */
    public PartyDetailsDto getPartyDetailsDtoById(int id) throws EntityNotFoundException {
        Party party = partyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Party not found"));
        return modelMapper.map(party, PartyDetailsDto.class);
    }

    /**
     * Joins a player to a party
     * @param partyId the id of the party
     * @param playerId the id of the player
     * @return the party with the player joined
     * @throws EntityNotFoundException if the party or player is not found
     */
    public PartyDetailsDto joinParty(int partyId, int playerId) throws EntityNotFoundException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new EntityNotFoundException("Party not found"));
        party.getParticipatingPlayers().add(playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found")));
        party = partyRepository.save(party);
        return modelMapper.map(party, PartyDetailsDto.class);
    }

    /**
     * Leaves a player from a party
     * @param partyId the id of the party
     * @param playerId the id of the player
     * @return the party with the player left
     */
    public PartyDetailsDto leaveParty(int partyId, int playerId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));
        party.getParticipatingPlayers().remove(playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found")));
        party = partyRepository.save(party);
        return modelMapper.map(party, PartyDetailsDto.class);
    }

    /**
     * Deletes a party from the database
     * @param partyId the id of the party to delete
     * @throws EntityNotFoundException if the party is not found
     */
    public void deleteParty(int partyId) throws EntityNotFoundException {
        Party party = partyRepository.findById(partyId).orElseThrow(() -> new EntityNotFoundException("Party not found"));

        for (Player player : party.getParticipatingPlayers()) {
            player.getParticipatingParties().remove(party);
        }
        party.getParticipatingPlayers().clear();

        for (Player player : party.getPlayersSaved()) {
            player.getSavedParties().remove(party);
        }
        party.getPlayersSaved().clear();

        party.getHost().getHostedParties().remove(party);
        party.setHost(null);

        party.getTags().forEach(tag -> tag.setParty(null));
        party.getTags().clear();

        party.setEdition(null);

        partyRepository.delete(party);
    }

}