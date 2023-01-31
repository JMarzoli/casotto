package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private final Reservation reservation = new Reservation();

    @Test
    void getId() {
        this.reservation.setId(0L);
        assertEquals(0L, this.reservation.getId());
    }

    @Test
    void getBookingDate() {
        LocalDate localDate = LocalDate.now();
        this.reservation.setBookingDate(localDate);
        assertEquals(localDate, this.reservation.getBookingDate());
    }

    @Test
    void getReservationBeginDate() {
        LocalDate localDate = LocalDate.now();
        this.reservation.setReservationBeginDate(localDate);
        assertEquals(localDate, this.reservation.getReservationBeginDate());
    }

    @Test
    void getReservationEndDate() {
        LocalDate localDate = LocalDate.now();
        this.reservation.setReservationEndDate(localDate);
        assertEquals(localDate, this.reservation.getReservationEndDate());
    }

    @Test
    void getReservationPrice() {
        this.reservation.setReservationPrice(10);
        assertEquals(10, this.reservation.getReservationPrice());
    }
}