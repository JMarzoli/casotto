package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Umbrella implements IUmbrella {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "umbrella_id")
    @SequenceGenerator(name = "umbrella_id", sequenceName = "umbrella_id", allocationSize = 1)
    private Long id;

    private double diameter;

    private String material;

    private double price;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Override
    public String toString() {
        return "Ombrello{" +
                "Diametro=" + diameter +
                ", Materiale='" + material + '\'' +
                ", Prezzo=" + price +
                '}';
    }
}
