package com.example.dndhub.services;

import com.example.dndhub.dtos.PartyDto;
import com.example.dndhub.models.Duration;
import com.example.dndhub.models.Party;
import com.example.dndhub.models.user.Player;
import com.example.dndhub.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    @Autowired
    public PartyService(PlayerRepository playerService, PartyRepository partyRepository, TagRepository tagRepository, EditionRepository editionRepository, PlaceRepository placeRepository) {
        this.playerRepository = playerService;
        this.partyRepository = partyRepository;
        this.tagRepository = tagRepository;
        this.editionRepository = editionRepository;
        this.placeRepository = placeRepository;
    }

    /**
     * Saves a party to the database
     * @param partyDto the party to save
     * @return the id of the saved party
     */
    @Transactional
    public int saveParty(PartyDto partyDto) {
        Party party = Party.builder()
                .name(partyDto.getName())
                .description(partyDto.getDescription())
                .maxPlayers(partyDto.getMaxPlayers())
                .duration(Duration.builder()
                        .startingDate(partyDto.getDuration().getStartingDate())
                        .endingDate(partyDto.getDuration().getEndingDate())
                        .build())
                .participatingPlayers(partyDto.getParticipatingPlayers().stream()
                        .map(playerDto -> playerRepository.findById(playerDto.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Player not found")))
                        .collect(Collectors.toSet()))
                .playersSaved(partyDto.getPlayersSaved().stream()
                        .map(playerDto -> playerRepository.findById(playerDto.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Player not found")))
                        .collect(Collectors.toSet()))
                .host(playerRepository.findById(partyDto.getHost().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Host not found")))
                .edition(editionRepository.findById(partyDto.getEdition().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Edition not found")))
                .tags(partyDto.getTags().stream()
                        .map(tagDto -> tagRepository.findById(tagDto.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Tag not found")))
                        .collect(Collectors.toSet()))
                .place(placeRepository.findById(partyDto.getPlace().getId())
                        .orElseThrow(() -> new EntityNotFoundException("Place not found")))
                .build();


        if (partyDto.getId() != 0) {
            party.setId(partyDto.getId());
        }

        party = partyRepository.saveAndFlush(party);
        return party.getId();
    }

    /**
     * Gets all parties from the database
     * @return a set of all parties
     */
    public Set<PartyDto> getAllParties() {
        return partyRepository.findAll().stream()
                .map(PartyService::getPartyDto)
                .collect(Collectors.toSet());
    }

    /**
     * Gets all parties from the database with associations included
     * @return a set of all parties with associations included
     */
    @Transactional
    public Set<PartyDto> getAllPartiesDeep() {
        return partyRepository.findAll().stream()
                .map(PartyService::getPartyDtoDeep)
                .collect(Collectors.toSet());
    }

    /**
     * Gets a party by id from the database
     * @param id the id of the party
     * @return the party with the given id
     * @throws EntityNotFoundException if the party is not found
     */
    public PartyDto getPartyById(int id) throws EntityNotFoundException {
        Party party = partyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Party not found"));
        return getPartyDto(party);
    }

    /**
     * Gets a party by id from the database with associations included
     * @param id the id of the party
     * @return the party with the given id with associations included
     * @throws EntityNotFoundException if the party is not found
     */
    public PartyDto getPartyByIdDeep(int id) throws EntityNotFoundException {
        Party party = partyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Party not found"));
        return getPartyDtoDeep(party);
    }

    /**
     * Converts a party entity to a party dto
     * @param party the party entity to convert
     * @return the party dto
     */
    static PartyDto getPartyDto(Party party) {
        return PartyDto.builder()
                .id(party.getId())
                .name(party.getName())
                .description(party.getDescription())
                .maxPlayers(party.getMaxPlayers())
                .duration(PartyDto.DurationDto.builder()
                        .startingDate(party.getDuration().getStartingDate())
                        .endingDate(party.getDuration().getEndingDate())
                        .build())
                .build();
    }

    /**
     * Converts a party entity to a party dto with included associations
     * @param party the party entity to convert
     * @return the party dto with associations included
     */
    static PartyDto getPartyDtoDeep(Party party) {
        return PartyDto.builder()
                .id(party.getId())
                .name(party.getName())
                .description(party.getDescription())
                .maxPlayers(party.getMaxPlayers())
                .duration(PartyDto.DurationDto.builder()
                        .startingDate(party.getDuration().getStartingDate())
                        .endingDate(party.getDuration().getEndingDate())
                        .build())
                .participatingPlayers(party.getParticipatingPlayers().stream()
                        .map(PlayerService::getPlayerDto)
                        .collect(Collectors.toSet()))
                .host(PlayerService.getPlayerDto(party.getHost()))
                .edition(EditionService.getEditionDto(party.getEdition()))
                .playersSaved(party.getPlayersSaved().stream()
                        .map(PlayerService::getPlayerDto)
                        .collect(Collectors.toSet()))
                .tags(party.getTags().stream()
                        .map(TagService::getTagDto)
                        .collect(Collectors.toSet()))
                .place(PlaceService.getPlaceDto(party.getPlace()))
                .build();
    }

    /**
     * Joins a player to a party
     * @param partyId the id of the party
     * @param playerId the id of the player
     * @return the party with the player joined
     * @throws EntityNotFoundException if the party or player is not found
     */
    public PartyDto joinParty(int partyId, int playerId) throws EntityNotFoundException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new EntityNotFoundException("Party not found"));
        party.getParticipatingPlayers().add(playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found")));
        party = partyRepository.saveAndFlush(party);
        return getPartyDtoDeep(party);
    }

    /**
     * Leaves a player from a party
     * @param partyId the id of the party
     * @param playerId the id of the player
     * @return the party with the player left
     */
    public PartyDto leaveParty(int partyId, int playerId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException("Party not found"));
        party.getParticipatingPlayers().remove(playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found")));
        party = partyRepository.saveAndFlush(party);
        return getPartyDtoDeep(party);
    }

    /**
     * Deletes a party from the database
     * @param partyId the id of the party to delete
     * @throws EntityNotFoundException if the party is not found
     */
    public void deleteParty(int partyId) throws EntityNotFoundException {
        Party party = partyRepository.findById(partyId).orElseThrow(() -> new EntityNotFoundException("Party not found"));

        for (Player player : party.getParticipatingPlayers()) {
            player.getParticipatedParties().remove(party);
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