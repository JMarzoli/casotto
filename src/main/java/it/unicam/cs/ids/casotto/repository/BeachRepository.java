package it.unicam.cs.ids.casotto.repository;

import it.unicam.cs.ids.casotto.model.Beach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeachRepository extends JpaRepository<Beach, Long> {
}
