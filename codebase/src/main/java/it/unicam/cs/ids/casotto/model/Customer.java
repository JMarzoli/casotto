package it.unicam.cs.ids.casotto.model;

import it.unicam.cs.ids.casotto.controller.ReservationManager;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Customer implements User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id")
    @SequenceGenerator(name = "customer_id", sequenceName = "customer_id", allocationSize = 1)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @ManyToMany(mappedBy = "customersInThisActivity", fetch = FetchType.EAGER)
    private Collection<Activity> bookedActivities;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && firstName.equals(customer.firstName) && lastName.equals(customer.lastName) && email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "Nome='" + firstName + '\'' +
                ", Cognome='" + lastName + '\'' +
                ", Email='" + email + '\'' +
                '}';
    }
}
