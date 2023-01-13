package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Location implements ILocation{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_id")
    @SequenceGenerator(name = "location_id", sequenceName = "location_id", allocationSize = 1)
    private Long id;

    @Column
    private String desc;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<Umbrella> umbrellas;

    @OneToMany(fetch = FetchType.EAGER)
    private Collection<BeachChair> beachChairs;

    public Location(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Location{" +
                "Descrizione='" + desc + '\'' +
                ", Ombrelli=" + umbrellas +
                ", Sdraio=" + beachChairs +
                '}';
    }
}
