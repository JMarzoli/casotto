package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Reservation implements IReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_id")
    @SequenceGenerator(name = "reservation_id", sequenceName = "reservation_id", allocationSize = 1)
    private Long id;
    @OneToOne(cascade = {CascadeType.MERGE},fetch= FetchType.EAGER)
    @JoinTable(
            name = "RESERVED_CUSTOMER",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID"))
    private Customer customer;
    @Column
    private LocalDate bookingDate;
    @Column
    private LocalDate reservationBeginDate;
    @Column
    private LocalDate reservationEndDate;
    @OneToOne(cascade = {CascadeType.MERGE},fetch= FetchType.EAGER)
    @JoinTable(
            name = "RESERVED_LOCATION",
            joinColumns = @JoinColumn(name = "RESERVATION_ID"),
            inverseJoinColumns = @JoinColumn(name = "LOCATION_ID"))
    private Location locationReserved;
    @Column
    private double reservationPrice;


    public Reservation(Customer customer, LocalDate bookingDate, LocalDate reservationBeginDate, LocalDate reservationEndDate, Location locationReserved, double reservationPrice) {
        this.customer = customer;
        this.bookingDate = bookingDate;
        this.reservationBeginDate = reservationBeginDate;
        this.reservationEndDate = reservationEndDate;
        this.locationReserved = locationReserved;
        this.reservationPrice = reservationPrice;
    }
}
