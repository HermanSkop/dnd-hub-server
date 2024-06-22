package com.example.dndhub.dtos;

import com.example.dndhub.models.place.OnlinePlatformType;
import lombok.*;

@Getter
@NoArgsConstructor
public class OnlinePlatformDto extends PlaceDto  {
    private OnlinePlatformType type;
    /**
     * The link to the platform's website.
     */
    private String link;
    /**
     * The path to the icon representing the platform.
     */
    private String iconPath;

    public void setType(OnlinePlatformType type) {
        if (type == null)
            throw new IllegalArgumentException("Type cannot be null");
        this.type = type;
    }

    public void setLink(String link) {
        if (link == null || link.isBlank())
            throw new IllegalArgumentException("Link cannot be null or blank");
        this.link = link;
    }

    public void setIconPath(String iconPath) {
        if (type == OnlinePlatformType.REGISTERED){
            if (iconPath == null || iconPath.isBlank())
                throw new IllegalArgumentException("Icon path cannot be null or blank for registered platforms");
        }
        else if (type == OnlinePlatformType.CUSTOM) {
            if (iconPath != null)
                throw new IllegalArgumentException("Icon path must be null for custom platforms");
        }
        this.iconPath = iconPath;
    }
}