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
public class Equipment implements IEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_id")
    @SequenceGenerator(name = "equipment_id", sequenceName = "equipment_id", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipment equipment = (Equipment) o;
        return id.equals(equipment.id) && name.equals(equipment.name) && description.equals(equipment.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
