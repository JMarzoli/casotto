package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Activity;
import it.unicam.cs.ids.casotto.repository.ActivityRepository;
import it.unicam.cs.ids.casotto.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
@Component
public class MailScheduler {

    private final ActivityRepository activityRepository;

    private final CustomerRepository customerRepository;

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailScheduler(ActivityRepository activityRepository, CustomerRepository customerRepository, JavaMailSender javaMailSender) {
        this.activityRepository = activityRepository;
        this.customerRepository = customerRepository;
        this.javaMailSender = javaMailSender;
    }

    @Scheduled(cron = "0 00 10 1/1 * ?")
    public void ssdInviaReminderGiornaliero() {
        ActivityManager activityManager = new ActivityManager(activityRepository, customerRepository);
        NotificationManager notificationManager = new NotificationManager(javaMailSender);
        List<Activity> activityList = activityManager.getAllActivities().stream().filter(activity -> LocalDate.now().isEqual(activity.getActivityBeginDate())).toList();
        activityList.forEach(activity ->
                activity.getCustomersInThisActivity().forEach(customer ->
                        notificationManager.sendSimpleMessage(customer.getEmail(), "Oggi inizierà un'attività alla quale sei iscritto!", activity.getInfo())));
    }
}