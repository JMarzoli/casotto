package it.unicam.cs.ids.casotto.repository;

import it.unicam.cs.ids.casotto.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
