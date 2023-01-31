package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    private final Manager manager = new Manager();

    @Test
    void getFirstName() {
        this.manager.setFirstName("Mario");
        assertEquals("Mario", this.manager.getFirstName());
    }

    @Test
    void getLastName() {
        this.manager.setLastName("Rossi");
        assertEquals("Rossi", this.manager.getLastName());
    }

    @Test
    void getEmail() {
        this.manager.setEmail("mario.rossi@gmail.com");
        assertEquals("mario.rossi@gmail.com", this.manager.getEmail());
    }
}