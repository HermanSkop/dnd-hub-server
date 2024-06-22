package com.example.dndhub.dtos;

import com.example.dndhub.configuration.AppConfig;
import com.example.dndhub.models.Party;
import com.example.dndhub.models.place.OnlinePlatform;
import com.example.dndhub.models.place.Place;
import com.example.dndhub.models.user.Player;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@NoArgsConstructor
public class PartyListItemDto {
    @Setter
    int id;
    String name;
    int maxPlayers;
    int participatingPlayers;
    String placeIconPath;

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be null or blank");
        if (name.length() < AppConfig.minPartyNameLength || name.length() > AppConfig.maxPartyNameLength)
            throw new IllegalArgumentException("Name must be between " + AppConfig.minPartyNameLength + " and "
                    + AppConfig.maxPartyNameLength + " characters");
        this.name = name;
    }

    public void setMaxPlayers(int maxPlayers) {
        if (maxPlayers < 1 || maxPlayers > Party.maxPossiblePlayers)
            throw new IllegalArgumentException("Max players must be between 1 and " + Party.maxPossiblePlayers);
        this.maxPlayers = maxPlayers;
    }

    public void setParticipatingPlayers(Set<Player> participatingPlayers) {
        if (participatingPlayers.size() > Party.maxPossiblePlayers)
            throw new IllegalArgumentException("Participating players must be between 0 and " + Party.maxPossiblePlayers);
        this.participatingPlayers = participatingPlayers.size();
    }

    public void setPlace(Place place) {
        if (place == null)
            throw new IllegalArgumentException("Place cannot be null");
        if (place.getClass() == OnlinePlatform.class) {
            OnlinePlatform onlinePlatform = (OnlinePlatform) place;
            if (onlinePlatform.getIconPath() == null || onlinePlatform.getIconPath().isBlank())
                throw new IllegalArgumentException("Place icon path cannot be blank");
            this.placeIconPath = onlinePlatform.getIconPath();
        }
    }
}
