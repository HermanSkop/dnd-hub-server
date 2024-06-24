package com.example.dndhub.configuration;

import com.example.dndhub.dtos.CurrentUserDto;
import com.example.dndhub.models.user.Admin;
import com.example.dndhub.models.user.Player;
import com.example.dndhub.models.user.User;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserConverter implements Converter<User, CurrentUserDto> {
    @Override
    public CurrentUserDto convert(org.modelmapper.spi.MappingContext<User, CurrentUserDto> mappingContext) {
        User source = mappingContext.getSource();
        CurrentUserDto destination = mappingContext.getDestination();
        if (destination == null) {
            destination = new CurrentUserDto();
        }

        destination.setId(source.getId());
        destination.setUsername(source.getUsername());
        destination.setEmail(source.getEmail());
        if (source.getClass() == Admin.class) {
            destination.setRole("ADMIN");
        }
        else if (source.getClass() == Player.class) {
            destination.setRole("PLAYER");
        }
        else {
            throw new IllegalArgumentException("Unknown user type");
        }

        return destination;
    }
}
