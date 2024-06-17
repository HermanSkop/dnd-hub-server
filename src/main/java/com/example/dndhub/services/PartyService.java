package com.example.dndhub.services;

import com.example.dndhub.dtos.PartyDto;
import com.example.dndhub.models.Duration;
import com.example.dndhub.models.Party;
import com.example.dndhub.models.user.Player;
import com.example.dndhub.models.user.User;
import com.example.dndhub.repositories.PartyRepository;
import com.example.dndhub.repositories.PlayerRepository;
import com.example.dndhub.repositories.TagRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PartyService {
    private final PlayerRepository playerRepository;
    private final PartyRepository partyRepository;
    private final TagRepository tagRepository;
    private final EntityManager entityManager;

    @Autowired
    public PartyService(PlayerRepository playerService, PartyRepository partyRepository, TagRepository tagRepository, EntityManager entityManager) {
        this.playerRepository = playerService;
        this.partyRepository = partyRepository;
        this.tagRepository = tagRepository;
        this.entityManager = entityManager;
    }

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
                .tags(partyDto.getTags().stream()
                        .map(tagDto -> tagRepository.findById(tagDto.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Tag not found")))
                        .collect(Collectors.toSet()))
                .build();


        if (partyDto.getId() != 0) {
            party.setId(partyDto.getId());
        }

        party = partyRepository.saveAndFlush(party);
        return party.getId();
    }

    public Set<PartyDto> getAllParties() {
        return partyRepository.findAll().stream()
                .map(this::getPartyDto)
                .collect(Collectors.toSet());
    }

    @Transactional
    public Set<PartyDto> getAllPartiesDeep() {
        return partyRepository.findAll().stream()
                .map(this::getPartyDtoDeep)
                .collect(Collectors.toSet());
    }

    public PartyDto getPartyById(int id) throws EntityNotFoundException {
        Party party = partyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Party not found"));
        return getPartyDto(party);
    }

    public PartyDto getPartyByIdDeep(int id) throws EntityNotFoundException {
        Party party = partyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Party not found"));
        return getPartyDtoDeep(party);
    }

    PartyDto getPartyDto(Party party) {
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

    PartyDto getPartyDtoDeep(Party party) {
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
                .playersSaved(party.getPlayersSaved().stream()
                        .map(PlayerService::getPlayerDto)
                        .collect(Collectors.toSet()))
                .build();
    }

    public PartyDto joinParty(int partyId, int playerId) throws EntityNotFoundException {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new EntityNotFoundException("Party not found"));
        party.getParticipatingPlayers().add(playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player not found")));
        party = partyRepository.saveAndFlush(party);
        return getPartyDtoDeep(party);
    }

    public PartyDto leaveParty(int id, int playerId) {
        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Party not found"));
        party.getParticipatingPlayers().remove(playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found")));
        party = partyRepository.saveAndFlush(party);
        return getPartyDtoDeep(party);
    }

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

        partyRepository.delete(party);
    }


}