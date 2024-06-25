package com.example.dndhub.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TagDto {
    @Setter
    private int id;
    private String value;
    private String iconPath;

    public void setValue(String value) {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("Value cannot be null or blank");
        this.value = value;
    }

    public void setIconPath(String iconPath) {
        if (iconPath == null || iconPath.isBlank())
            throw new IllegalArgumentException("Icon path cannot be null or blank");
        this.iconPath = iconPath;
    }
}
