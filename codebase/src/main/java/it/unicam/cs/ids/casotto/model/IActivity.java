package it.unicam.cs.ids.casotto.model;

import java.time.LocalDate;

public interface IActivity {

    String getInfo();

    void setInfo(String info);

    LocalDate getActivityBeginDate();

    void setActivityBeginDate(LocalDate activityBeginDate);

    LocalDate getActivityEndDate();

    void setActivityEndDate(LocalDate activityEndDate);
}
