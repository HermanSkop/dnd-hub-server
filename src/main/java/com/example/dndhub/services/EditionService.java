package com.example.dndhub.services;

import com.example.dndhub.dtos.EditionDto;
import com.example.dndhub.models.edition.Edition;
import com.example.dndhub.repositories.EditionRepository;
import com.example.dndhub.repositories.PartyRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class EditionService {
    private final PartyRepository partyRepository;
    private final EditionRepository editionRepository;

    @Autowired
    public EditionService(PartyRepository partyRepository, EditionRepository editionRepository) {
        this.partyRepository = partyRepository;
        this.editionRepository = editionRepository;
    }

    public static EditionDto getEditionDto(Edition edition) {
        return EditionDto.builder()
                .id(edition.getId())
                .name(edition.getName())
                .description(edition.getDescription())
                .releaseYear(edition.getReleaseYear())
                .type(edition.getType())
                .build();
    }
    public static EditionDto getEditionDtoDeep(Edition edition) {
        return EditionDto.builder()
                .id(edition.getId())
                .name(edition.getName())
                .description(edition.getDescription())
                .releaseYear(edition.getReleaseYear())
                .type(edition.getType())
                .parties(edition.getParties().stream()
                        .map(PartyService::getPartyDto)
                        .collect(Collectors.toSet()))
                .build();
    }



    @Transactional
    public int saveEdition(EditionDto editionDto) {
        Edition edition = Edition.builder()
                .name(editionDto.getName())
                .description(editionDto.getDescription())
                .releaseYear(editionDto.getReleaseYear())
                .type(editionDto.getType())
                .parties(editionDto.getParties().stream()
                        .map(partyDto -> partyRepository.findById(partyDto.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Party not found")))
                        .collect(Collectors.toSet()))
                .build();
        return editionRepository.save(edition).getId();
    }

    public EditionDto getEditionById(int id) {
        Edition edition = editionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Edition not found"));
        return EditionDto.builder()
                .id(edition.getId())
                .name(edition.getName())
                .description(edition.getDescription())
                .releaseYear(edition.getReleaseYear())
                .type(edition.getType())
                .build();
    }
}
