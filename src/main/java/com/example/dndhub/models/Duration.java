package com.example.dndhub.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Duration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private static short maxDaysDuration = 365;
    @NotNull(message = "Starting date is required")
    private LocalDate startingDate;
    private LocalDate endingDate;

    @NotNull(message = "Party is required")
    @OneToOne(mappedBy = "duration")
    private Party party;

    @PreUpdate
    @PrePersist
    private void validate() {
        if (endingDate != null) {
            if (startingDate.isAfter(endingDate)) {
                throw new IllegalArgumentException("Starting date must be before ending date");
            }
            if (startingDate.plusDays(maxDaysDuration).isBefore(endingDate)) {
                throw new IllegalArgumentException("Duration must be less than " + maxDaysDuration + " days");
            }
        }
    }
}
