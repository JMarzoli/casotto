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
public class BeachChair implements IBeachChair {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "beachChair_id")
    @SequenceGenerator(name = "beachChair_id", sequenceName = "beachChair_id", allocationSize = 1)
    private Long id;
    @Column
    private String material;
    @Column
    private double length;
    @Column
    private double width;
    @Column
    private double price;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BeachChair that = (BeachChair) o;
        return Double.compare(that.length, length) == 0 && Double.compare(that.width, width) == 0 && Double.compare(that.price, price) == 0 && id.equals(that.id) && material.equals(that.material);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, material, length, width, price);
    }

    @Override
    public String toString() {
        return "Sdraio{" +
                "Materiale='" + material + '\'' +
                ", Lunghezza=" + length +
                ", Larghezza=" + width +
                ", Prezzo=" + price +
                '}';
    }
}
