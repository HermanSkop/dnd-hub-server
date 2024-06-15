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
    @NotNull
    private LocalDate startingDate;
    private LocalDate endingDate;

    @OneToOne(mappedBy = "duration")
    private Party party;

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Duration duration = (Duration) o;

        return id == duration.id;
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
