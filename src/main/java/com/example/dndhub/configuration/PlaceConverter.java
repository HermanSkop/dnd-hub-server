package com.example.dndhub.configuration;

import com.example.dndhub.dtos.OnlinePlatformDto;
import com.example.dndhub.dtos.PlaceDto;
import com.example.dndhub.models.place.OnlinePlatform;
import com.example.dndhub.models.place.Place;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class PlaceConverter implements Converter<Place, PlaceDto> {
    @Override
    public PlaceDto convert(MappingContext<Place, PlaceDto> mappingContext) {
        Place source = mappingContext.getSource();
        PlaceDto destination = mappingContext.getDestination();
        if (destination == null) {
            destination = new PlaceDto();
        }

        destination.setId(source.getId());
        destination.setName(source.getName());

        if (source instanceof OnlinePlatform onlinePlatform) {
            OnlinePlatformDto onlinePlatformDto = new OnlinePlatformDto();
            onlinePlatformDto.setId(onlinePlatform.getId());
            onlinePlatformDto.setName(onlinePlatform.getName());
            onlinePlatformDto.setLink(onlinePlatform.getLink());
            onlinePlatformDto.setIconPath(onlinePlatform.getIconPath());
            return onlinePlatformDto;
        }

        return destination;
    }
}
