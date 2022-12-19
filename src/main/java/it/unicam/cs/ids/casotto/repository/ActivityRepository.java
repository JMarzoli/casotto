package it.unicam.cs.ids.casotto.repository;

import it.unicam.cs.ids.casotto.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
