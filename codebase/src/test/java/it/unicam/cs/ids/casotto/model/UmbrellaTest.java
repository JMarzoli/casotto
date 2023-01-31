package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class UmbrellaTest {

    private final Umbrella umbrella = new Umbrella();

    @Test
    void getId() {
        this.umbrella.setId(0L);
        assertEquals(0L, this.umbrella.getId());
    }

    @Test
    void getDiameter() {
        this.umbrella.setDiameter(10.9);
        assertEquals(10.9, this.umbrella.getDiameter());
    }

    @Test
    void getMaterial() {
        this.umbrella.setMaterial("Plastica");
        assertEquals("Plastica", this.umbrella.getMaterial());
    }

    @Test
    void getPrice() {
        this.umbrella.setPrice(6);
        assertEquals(6, this.umbrella.getPrice());
    }

    @Test
    void getLocation() {
        Location location = new Location();
        this.umbrella.setLocation(location);
        assertEquals(location, this.umbrella.getLocation());
    }
}