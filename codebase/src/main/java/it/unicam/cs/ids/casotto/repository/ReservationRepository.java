package it.unicam.cs.ids.casotto.repository;

import it.unicam.cs.ids.casotto.model.Equipment;
import it.unicam.cs.ids.casotto.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
