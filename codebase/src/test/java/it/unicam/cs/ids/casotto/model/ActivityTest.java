package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    private final Activity activity = new Activity();

    @Test
    void getId() {
        this.activity.setId(0L);
        assertEquals(0L, this.activity.getId());
    }

    @Test
    void getInfo() {
        this.activity.setInfo("Informazioni");
        assertEquals("Informazioni", this.activity.getInfo());
    }

    @Test
    void getActivityBeginDate() {
        LocalDate localDate = LocalDate.now();
        this.activity.setActivityBeginDate(localDate);
        assertEquals(localDate, this.activity.getActivityBeginDate());
    }

    @Test
    void getActivityEndDate() {
        LocalDate localDate = LocalDate.now();
        this.activity.setActivityEndDate(localDate);
        assertEquals(localDate, this.activity.getActivityEndDate());
    }

    @Test
    void getCurrentNumberOfPeople() {
        this.activity.setCurrentNumberOfPeople(10);
        assertEquals(10, this.activity.getCurrentNumberOfPeople());
    }

    @Test
    void getMaxNumberOfPeople() {
        this.activity.setMaxNumberOfPeople(10);
        assertEquals(10, this.activity.getMaxNumberOfPeople());
    }
}