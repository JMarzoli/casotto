package it.unicam.cs.ids.casotto.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Manager implements User{

    private String firstName;

    private String lastName;

    private String email;
}
