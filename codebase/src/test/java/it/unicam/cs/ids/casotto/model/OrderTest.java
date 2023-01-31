package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private final Order order = new Order();

    @Test
    void getId() {
        this.order.setId(0L);
        assertEquals(0L, this.order.getId());
    }

    @Test
    void getPrice() {
        this.order.setPrice(10.5);
        assertEquals(10.5, this.order.getPrice());
    }

    @Test
    void isHasBeenCompleted() {
        this.order.setHasBeenCompleted(true);
        assertTrue(this.order.isHasBeenCompleted());
    }
}