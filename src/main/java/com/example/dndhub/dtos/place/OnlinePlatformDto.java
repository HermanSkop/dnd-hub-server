package com.example.dndhub.dtos.place;

import com.example.dndhub.models.place.OnlinePlatformType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OnlinePlatformDto extends PlaceDto implements Serializable {
    @NotNull(message = "Link is mandatory")
    @NotBlank(message = "Link is mandatory")
    @URL
    private String link;
    @NotNull(message = "Icon is mandatory")
    @NotBlank(message = "Icon is mandatory")
    @URL
    private String iconPath;
    @NotNull(message = "Type is mandatory")
    private OnlinePlatformType type;
}