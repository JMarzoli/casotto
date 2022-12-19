package it.unicam.cs.ids.casotto.repository;

import it.unicam.cs.ids.casotto.model.BeachChair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeachChairRepository extends JpaRepository<BeachChair, Long> {
}
