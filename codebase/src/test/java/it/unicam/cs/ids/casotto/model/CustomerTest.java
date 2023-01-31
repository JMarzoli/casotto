package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    private final Customer customer = new Customer();

    @Test
    void getId() {
        this.customer.setId(0L);
        assertEquals(0L, this.customer.getId());
    }

    @Test
    void getFirstName() {
        this.customer.setFirstName("Mario");
        assertEquals("Mario", this.customer.getFirstName());
    }

    @Test
    void getLastName() {
        this.customer.setLastName("Rossi");
        assertEquals("Rossi", this.customer.getLastName());
    }

    @Test
    void getEmail() {
        this.customer.setEmail("mario.rossi@gmail.com");
        assertEquals("mario.rossi@gmail.com", this.customer.getEmail());
    }
}