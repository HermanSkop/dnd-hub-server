package com.example.dndhub.services;

import com.example.dndhub.dtos.PartyDto;
import com.example.dndhub.dtos.TagDto;
import com.example.dndhub.models.Tag;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    public static TagDto getTagDto(Tag tag) {
        return new TagDto(tag.getId(), tag.getValue(), tag.getIconPath(), null);
    }

    public static TagDto getTagDtoDeep(Tag tag) {
        return new TagDto(tag.getId(), tag.getValue(), tag.getIconPath(), PartyService.getPartyDto(tag.getParty()));
    }
}
