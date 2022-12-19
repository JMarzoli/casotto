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
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sector_id")
    @SequenceGenerator(name = "sector_id", sequenceName = "sector_id", allocationSize = 1)
    private Long id;
    @Column
    private double costFactor;
}
