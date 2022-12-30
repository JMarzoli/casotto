package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Order;
import it.unicam.cs.ids.casotto.model.Product;
import it.unicam.cs.ids.casotto.repository.OrderRepository;
import it.unicam.cs.ids.casotto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BarController {

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public BarController(ProductRepository productRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public void addNewProduct(Product product) {
        this.productRepository.save(product);
    }
    public List<Product> getProducts() {
        return this.productRepository.findAll();
    }

    public void createNewOrder(List<Product> products) {
        Order order = new Order();
        order.setProducts(products);
        this.orderRepository.save(order);
    }
    public void setOrderAsCompleted(Long orderId) {
        Order order = this.orderRepository.getReferenceById(orderId);
        order.setHasBeenCompleted(true);
        this.orderRepository.save(order);
    }
}
