package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
    private List<Umbrella> umbrellas;

    @OneToMany(fetch = FetchType.EAGER)
    private List<BeachChair> beachChairs;
    @Column
    private String qrCode;

    public Location(String desc) {
        this.desc = desc;
    }

    public Location(String desc, List<Umbrella> umbrellas, List<BeachChair> beachSeats, String qrCode) {
        this.desc = desc;
        this.umbrellas = umbrellas;
        this.beachChairs = beachSeats;
        this.qrCode = qrCode;
    }

    @Override
    public String toString() {
        return "Location{" +
                "Descrizione='" + desc + '\'' +
                ", Ombrelli=" + umbrellas +
                ", Sdraio=" + beachChairs +
                ", QrCode=" + qrCode +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(desc, location.desc) && Objects.equals(qrCode, location.qrCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, desc, qrCode);
    }
}
