package it.unicam.cs.ids.casotto.model;

import lombok.Setter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SectorTest {

    private final Sector sector = new Sector();

    @Test
    void getId() {
        this.sector.setId(0L);
        assertEquals(0L, this.sector.getId());
    }

    @Test
    void getCostFactor() {
        this.sector.setCostFactor(10);
        assertEquals(10, this.sector.getCostFactor());
    }
}