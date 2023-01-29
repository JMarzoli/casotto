package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Customer;
import it.unicam.cs.ids.casotto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerManager {

    private final CustomerRepository customerRepository;

    private ReservationManager reservationManager;

    @Autowired
    public CustomerManager(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer) {
        this.customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

}
