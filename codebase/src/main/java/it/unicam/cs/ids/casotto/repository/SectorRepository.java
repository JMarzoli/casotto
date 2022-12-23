package it.unicam.cs.ids.casotto.repository;

import it.unicam.cs.ids.casotto.model.Activity;
import it.unicam.cs.ids.casotto.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
}
