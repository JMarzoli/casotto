package it.unicam.cs.ids.casotto.repository;

import it.unicam.cs.ids.casotto.model.Umbrella;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UmbrellaRepository extends JpaRepository<Umbrella, Long> {
}
