package com.example.dndhub.services;

import com.example.dndhub.dtos.OnlinePlatformDto;
import com.example.dndhub.dtos.PlaceDto;
import com.example.dndhub.models.place.OfflinePlace;
import com.example.dndhub.models.place.OnlinePlatform;
import com.example.dndhub.models.place.Place;
import com.example.dndhub.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public static PlaceDto getPlaceDto(Place place) {
        if (place instanceof OnlinePlatform onlinePlatform) {
            return new OnlinePlatformDto(onlinePlatform.getId(), onlinePlatform.getName(), onlinePlatform.getLink(),
                    onlinePlatform.getIconPath(), onlinePlatform.getType(), null);
        } else {
            throw new IllegalArgumentException("Place type not supported");
        }
    }

    public static PlaceDto getPlaceDtoDeep(Place place) {
        if (place instanceof OnlinePlatform onlinePlatform) {
            return new OnlinePlatformDto(onlinePlatform.getId(), onlinePlatform.getName(), onlinePlatform.getLink(),
                    onlinePlatform.getIconPath(), onlinePlatform.getType(), onlinePlatform.getParties()
                        .stream()
                        .map(PartyService::getPartyDto)
                        .collect(Collectors.toSet()));
        } else {
            throw new IllegalArgumentException("Place type not supported");
        }
    }

    public int savePlace(PlaceDto placeDto) {
        if (placeDto instanceof OnlinePlatformDto onlinePlatformDto) {
            OnlinePlatform place = OnlinePlatform.builder()
                    .name(onlinePlatformDto.getName())
                    .link(onlinePlatformDto.getLink())
                    .iconPath(onlinePlatformDto.getIconPath())
                    .type(onlinePlatformDto.getType())
                    .build();
            return placeRepository.save(place).getId();
        } else {
            throw new IllegalArgumentException("Place type not supported");
        }
    }
}
