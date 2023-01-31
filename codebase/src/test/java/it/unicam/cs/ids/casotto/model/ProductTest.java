package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private final Product product = new Product();

    @Test
    void getId() {
        this.product.setId(0L);
        assertEquals(0L, this.product.getId());
    }

    @Test
    void getName() {
        this.product.setName("Succo");
        assertEquals("Succo", this.product.getName());
    }

    @Test
    void getPrice() {
        this.product.setPrice(5.90);
        assertEquals(5.90, this.product.getPrice());
    }

    @Test
    void getDescription() {
        this.product.setDescription("Succo alla pesca");
        assertEquals("Succo alla pesca", this.product.getDescription());
    }

    @Test
    void getQuantity() {
        this.product.setQuantity(2);
        assertEquals(2, this.product.getQuantity());
    }
}