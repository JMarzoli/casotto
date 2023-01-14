package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Customer;
import it.unicam.cs.ids.casotto.model.Manager;
import it.unicam.cs.ids.casotto.repository.CustomerRepository;
import it.unicam.cs.ids.casotto.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerManager {
    private final ManagerRepository managerRepository;
    private ReservationManager reservationManager;
    @Autowired
    public ManagerManager(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    public void saveManager(Manager manager) {
        this.managerRepository.save(manager);
    }
}
