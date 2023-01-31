package it.unicam.cs.ids.casotto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receipt receipt = (Receipt) o;
        return Double.compare(receipt.price, price) == 0 && receiptDateTime.equals(receipt.receiptDateTime) && order.equals(receipt.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptDateTime, order, price);
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
