package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StructureTest {

    private final Structure structure = new Structure();

    @Test
    void getBase() {
        this.structure.setBase(200);
        assertEquals(200, this.structure.getBase());
    }

    @Test
    void getHeight() {
        this.structure.setHeight(300);
        assertEquals(300, this.structure.getHeight());
    }
}