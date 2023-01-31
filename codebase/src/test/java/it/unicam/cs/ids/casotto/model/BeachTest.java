package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BeachTest {

    private final Beach beach = new Beach();

    @Test
    void getId() {
        this.beach.setId(0L);
        assertEquals(0L, this.beach.getId());
    }

    @Test
    void getSandType() {
        this.beach.setSandType("Rocciosa");
        assertEquals("Rocciosa", this.beach.getSandType());
    }

    @Test
    void getDescription() {
        this.beach.setDescription("Bellissima spiaggia con mare cristallino");
        assertEquals("Bellissima spiaggia con mare cristallino", this.beach.getDescription());
    }
}