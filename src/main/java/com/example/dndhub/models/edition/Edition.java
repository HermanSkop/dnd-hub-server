package com.example.dndhub.models.edition;

import com.example.dndhub.models.Party;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class Edition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;
    @NotNull(message = "Name is mandatory")
    @NotBlank(message = "Name is mandatory")
    protected String name;
    /**
     * Full description of the setting and system. Must be at most 5000 characters.
     * If the edition is official, this field must be null.
     */
    @Column(length = 5000)
    protected String description;
    /**
     * The year the edition was released. Must be null for custom editions.
     */
    private Integer releaseYear;
    @NotNull(message = "Type is mandatory")
    @Enumerated(EnumType.STRING)
    private EditionType type;

    @PrePersist
    @PreUpdate
    private void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is mandatory");
        }
        if (type == null) {
            throw new IllegalArgumentException("Type is mandatory");
        }
        if (type == EditionType.CUSTOM){
            if (description == null || description.isBlank())
                throw new IllegalArgumentException("Description is mandatory");
            if (releaseYear != null)
                throw new IllegalArgumentException("Release year must be null for custom editions");
        }
        if (type == EditionType.OFFICIAL){
            if (description != null)
                throw new IllegalArgumentException("Description must be null for official editions");
            if (releaseYear == null)
                throw new IllegalArgumentException("Release year is mandatory for official editions");
        }
        if (releaseYear != null && releaseYear < 1974 && releaseYear > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("Release year must be greater than 1974");
        }
    }

    @ToString.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "edition")
    private Set<Party> parties = new HashSet<>();
}