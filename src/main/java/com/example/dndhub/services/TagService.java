package com.example.dndhub.services;

import com.example.dndhub.dtos.TagDto;
import com.example.dndhub.models.Tag;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    /**
     * Converts a Tag entity to a TagDto
     * @param tag Tag entity
     * @return TagDto
     */
    public static TagDto getTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getValue(), tag.getIconPath(), null);
    }

    /**
     * Converts a Tag entity to a TagDto with associations included
     * @param tag Tag entity
     * @return TagDto
     */
    public static TagDto getTagDtoDeep(Tag tag) {
        return new TagDto(tag.getId(), tag.getValue(), tag.getIconPath(), PartyService.getPartyDto(tag.getParty()));
    }
}
