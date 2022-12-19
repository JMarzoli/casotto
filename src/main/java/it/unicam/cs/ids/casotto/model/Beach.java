package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Beach implements IBeach {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beach_id")
    @SequenceGenerator(name = "beach_id", sequenceName = "beach_id", allocationSize = 1)
    private Long id;

    @Column
    private String sandType;

    @Column
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beach beach = (Beach) o;
        return id.equals(beach.id) && sandType.equals(beach.sandType) && description.equals(beach.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sandType, description);
    }
}
