package it.unicam.cs.ids.casotto.model;

import java.time.LocalDateTime;

public class Receipt {

    private LocalDateTime receiptDateTime;

    private final Order order;

    private double price;

    public Receipt (LocalDateTime receiptDateTime, Order order, double price) {
        this.receiptDateTime = receiptDateTime;
        this.order = order;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "receiptDateTime=" + receiptDateTime +
                ", order=" + order +
                ", price=" + price +
                '}';
    }
}
