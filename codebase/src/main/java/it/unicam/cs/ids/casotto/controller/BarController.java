package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Order;
import it.unicam.cs.ids.casotto.model.Product;
import it.unicam.cs.ids.casotto.model.Receipt;
import it.unicam.cs.ids.casotto.repository.OrderRepository;
import it.unicam.cs.ids.casotto.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
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

    public List<Order> getAllOrders(){
        return this.orderRepository.findAll();
    }

    /**
     * Provides the non completed orders
     * @return a List of order with the boolean parameters set on false
     */
    public List<Order> getNonCompletedOrder() {
        List<Order> orderList = this.getAllOrders();
        List<Order> nonCompletedOrders = orderList.stream().filter(Order::isHasBeenCompleted).toList();
        orderList.removeAll(nonCompletedOrders);
        return orderList;
    }

    public void createNewOrder(List<Product> products) {
        Order order = new Order();
        order.setProducts(products);
        double price = 0;
        for (Product product : products) {
            if (product.getQuantity() <= 0) {
                System.out.println("Il prodotto " + product.getName() + " non è più disponibile");
            } else {
                price += product.getPrice();
                product.setQuantity(product.getQuantity() - 1);
                this.productRepository.save(product);
            }
        }
        order.setPrice(price);
        this.orderRepository.save(order);
    }

    public void setOrderAsCompleted(Long orderId) {
        Order order = this.orderRepository.findById(orderId).orElse(null);
        assert order != null;
        order.setHasBeenCompleted(true);
        this.orderRepository.save(order);
    }

    /**
     * Creates a new receipt for an order
     * @param order the order of interest
     * @return a receipt
     */
    public Receipt createRecipt(Order order) {
        LocalDateTime time = LocalDateTime.now();
        Receipt receipt = new Receipt(time, order, order.getPrice());
        return receipt;
    }

    public void createProduct(String name, String desc, double price, int quantity) {
        productRepository.save(new Product(name,price,desc,quantity));
    }
}
