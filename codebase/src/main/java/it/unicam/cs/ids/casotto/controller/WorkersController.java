package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Order;
import it.unicam.cs.ids.casotto.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WorkersController {

    private final OrderRepository orderRepository;

    @Autowired
    public WorkersController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrdersNonCompleted() {
        return this.orderRepository.findAll().stream().filter(order -> !order.isHasBeenCompleted()).toList();
    }

    public void setOrderCompleted(Long orderId) {
        Order order = this.orderRepository.findAll().stream().filter(order1 -> order1.getId().equals(orderId)).findFirst().orElseThrow((NullPointerException::new));
        order.setHasBeenCompleted(true);
        this.orderRepository.save(order);
    }
}
