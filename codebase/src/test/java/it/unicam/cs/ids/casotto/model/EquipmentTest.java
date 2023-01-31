package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EquipmentTest {

    private final Equipment equipment = new Equipment();

    @Test
    void getId() {
        this.equipment.setId(0L);
        assertEquals(0L, this.equipment.getId());
    }

    @Test
    void getName() {
        this.equipment.setName("Equipaggiamento");
        assertEquals("Equipaggiamento", this.equipment.getName());
    }

    @Test
    void getDescription() {
        this.equipment.setDescription("Descrizione Equipaggiamento");
        assertEquals("Descrizione Equipaggiamento", this.equipment.getDescription());
    }
}