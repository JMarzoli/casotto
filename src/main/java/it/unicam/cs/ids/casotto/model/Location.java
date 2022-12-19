package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private List<Umbrella> umbrellas;
    private List<BeachChair> beachChairs;

    public Location(Long id, String desc) {
        this.id = id;
        this.desc = desc;
    }
}
