package it.unicam.cs.ids.casotto.model;

import java.time.LocalDate;

public interface IDiscountCode {

    String getCode();

    void setCode(String code);
    
    LocalDate getBeginningValidityDate();

    void setBeginningValidityDate(LocalDate beginningValidityDate);

    LocalDate getEndingValidityDate();

    void setEndingValidityDate(LocalDate endingValidityDate);
}
