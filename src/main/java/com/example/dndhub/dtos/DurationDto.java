package com.example.dndhub.dtos;

import com.example.dndhub.models.Duration;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class DurationDto {
    private LocalDate startingDate;
    /**
     * The date when the party ends. Can be null and must be after the starting date.
     */
    private LocalDate endingDate;

    public void setStartingDate(LocalDate startingDate) {
        if (startingDate == null)
            throw new IllegalArgumentException("Starting date cannot be null");
        if (endingDate != null) {
            if (startingDate.isAfter(endingDate))
                throw new IllegalArgumentException("Starting date must be before ending date");
            if (startingDate.plusDays(Duration.maxDaysDuration).isBefore(endingDate))
                throw new IllegalArgumentException("Duration must be less than " + Duration.maxDaysDuration + " days");
        }
        this.startingDate = startingDate;
    }

    public void setEndingDate(LocalDate endingDate) {
        if (endingDate != null) {
            if (startingDate != null) {
                if (startingDate.isAfter(endingDate))
                    throw new IllegalArgumentException("Starting date must be before ending date");
                if (startingDate.plusDays(Duration.maxDaysDuration).isBefore(endingDate))
                    throw new IllegalArgumentException("Duration must be less than " + Duration.maxDaysDuration + " days");
            }
        }
        this.endingDate = endingDate;
    }

}
