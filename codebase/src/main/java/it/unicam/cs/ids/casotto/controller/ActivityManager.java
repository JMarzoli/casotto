package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Activity;
import it.unicam.cs.ids.casotto.model.Customer;
import it.unicam.cs.ids.casotto.repository.ActivityRepository;
import it.unicam.cs.ids.casotto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public List<Activity> getAllActivities() {
        return this.activityRepository.findAll();
    }

    public boolean addCustomerToActivity(long customerId, long activityId) {
        Customer customer = this.customerRepository.findById(customerId).orElse(null);
        Activity activity = this.activityRepository.findById(activityId).orElse(null);
        if ((customer != null && activity != null) && (activity.getCurrentNumberOfPeople() < activity.getMaxNumberOfPeople() || activity.getMaxNumberOfPeople() < 0)) {
            activity.getCustomersInThisActivity().add(customer);
            activity.setCurrentNumberOfPeople(activity.getCurrentNumberOfPeople() + 1);
            this.activityRepository.save(activity);
            return true;
        }
        return false;
    }

    public void updateActivity(Activity updatedActivity) {
        if (this.activityRepository.findById(updatedActivity.getId()).isPresent()) {
            this.activityRepository.save(updatedActivity);
        }
    }

    public void deleteActivity(Long id) {
        this.activityRepository.deleteById(id);
    }
}
