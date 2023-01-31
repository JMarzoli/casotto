package it.unicam.cs.ids.casotto.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DiscountCodeTest {

    private final DiscountCode discountCode = new DiscountCode();

    @Test
    void getCode() {
        this.discountCode.setCode("PROMO50");
        assertEquals("PROMO50", this.discountCode.getCode());
    }

    @Test
    void getBeginningValidityDate() {
        LocalDate localDate = LocalDate.now();
        this.discountCode.setBeginningValidityDate(localDate);
        assertEquals(localDate, this.discountCode.getBeginningValidityDate());
    }

    @Test
    void getEndingValidityDate() {
        LocalDate localDate = LocalDate.now();
        this.discountCode.setEndingValidityDate(localDate);
        assertEquals(localDate, this.discountCode.getEndingValidityDate());
    }
}