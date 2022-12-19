package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Activity;
import it.unicam.cs.ids.casotto.model.Customer;
import it.unicam.cs.ids.casotto.repository.ActivityRepository;
import it.unicam.cs.ids.casotto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ActivityManager {

    private final ActivityRepository activityRepository;

    private final CustomerRepository customerRepository;

    @Autowired
    public ActivityManager(ActivityRepository activityRepository, CustomerRepository customerRepository) {
        this.activityRepository = activityRepository;
        this.customerRepository = customerRepository;
    }

    public void saveActivity(Activity activity) {
        this.activityRepository.save(activity);
    }

    public void addCustomerToActivity(long customerId, long activityId) {
        Optional<Customer> customer = this.customerRepository.findById(customerId);
        Customer customer1 = customer.orElse(null);
        Optional<Activity> activity = this.activityRepository.findById(activityId);
        Activity activity1 = activity.orElse(null);
        activity1.getCustomersInThisActivity().add(customer1);
        this.activityRepository.save(activity1);
    }
}
