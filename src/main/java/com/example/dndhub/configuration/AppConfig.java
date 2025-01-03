package com.example.dndhub.configuration;

import com.example.dndhub.dtos.CurrentUserDto;
import com.example.dndhub.dtos.PlaceDto;
import com.example.dndhub.models.place.Place;
import com.example.dndhub.models.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String usernameRegex = "^[a-zA-Z0-9_+&*-]{3,20}$";
    public static final String passwordRegex = "^[a-zA-Z0-9_+&*-]{3,20}$";
    public static final int minUsernameLength = 3;
    public static final int maxUsernameLength = 20;
    public static final int minPartyNameLength = 3;
    public static final int maxPartyNameLength = 30;
    public static final int maxPartyDescriptionLength = 2000;
    public static final int minPlaceNameLength = 3;
    public static final int maxPlaceNameLength = 30;
    public static final int minEditionNameLength = 3;
    public static final int maxEditionNameLength = 30;
    public static final int maxEditionDescriptionLength = 5000;
    public static final int testPlayerId = 9;


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Place.class, PlaceDto.class)
                .setConverter(new PlaceConverter());

        modelMapper.createTypeMap(User.class, CurrentUserDto.class)
                .setConverter(new CurrentUserConverter());

        return modelMapper;
    }
}
