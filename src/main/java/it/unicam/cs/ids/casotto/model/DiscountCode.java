package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class DiscountCode implements IDiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discount_id")
    @SequenceGenerator(name = "discount_id", sequenceName = "discount_id", allocationSize = 1)
    private Long id;

    @Column
    private String code;

    @Column
    private LocalDate beginningValidityDate;

    @Column
    private LocalDate endingValidityDate;

    public DiscountCode(String sconto) {
        this.code = sconto;
    }
}
