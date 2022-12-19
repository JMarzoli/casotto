package it.unicam.cs.ids.casotto.model;

import java.time.LocalDate;

public interface IReservation {

    LocalDate getBookingDate();

    void setBookingDate(LocalDate bookingDate);

    LocalDate getReservationBeginDate();

    void setReservationBeginDate(LocalDate reservationBeginDate);

    LocalDate getReservationEndDate();

    void setReservationEndDate(LocalDate reservationEndDate);
}
