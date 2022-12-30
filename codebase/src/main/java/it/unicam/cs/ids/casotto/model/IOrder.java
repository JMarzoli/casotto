package it.unicam.cs.ids.casotto.model;

public interface IOrder {

    Long getId();

    Customer getCustomer();

    double getPrice();
}
