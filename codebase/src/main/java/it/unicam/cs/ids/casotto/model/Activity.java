package it.unicam.cs.ids.casotto.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Activity implements IActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_id")
    @SequenceGenerator(name = "activity_id", sequenceName = "activity_id", allocationSize = 1)
    private Long id;

    @Column
    private String info;

    @Column
    private LocalDate activityBeginDate;

    @Column
    private LocalDate activityEndDate;

    @Column
    private int currentNumberOfPeople;

    @Column
    private int maxNumberOfPeople;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ACTIVITIES_CUSTOMERS",
            joinColumns = @JoinColumn(name = "ACTIVITY_ID"),
            inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID"))
    private Collection<Customer> customersInThisActivity;

    public Activity(String info, LocalDate dataInizio, LocalDate dataFine) {
        this.info = info;
        this.activityBeginDate = dataInizio;
        this.activityEndDate = dataFine;
        this.currentNumberOfPeople = 0;
        this.maxNumberOfPeople = -1;
        customersInThisActivity = new ArrayList<>();
    }

    public Activity(String info, LocalDate dataInizio, LocalDate dataFine, int numPartecipanti) {
        this.info = info;
        this.activityBeginDate = dataInizio;
        this.activityEndDate = dataFine;
        this.maxNumberOfPeople = numPartecipanti;
        this.currentNumberOfPeople = 0;
        customersInThisActivity = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return maxNumberOfPeople == activity.maxNumberOfPeople && id.equals(activity.id) && info.equals(activity.info) && activityBeginDate.equals(activity.activityBeginDate) && activityEndDate.equals(activity.activityEndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, info, activityBeginDate, activityEndDate, maxNumberOfPeople);
    }

    @Override
    public String toString() {
        String toString =  "ID ATTIVITA' = " + id + ", INFORMAZIONI = " + info + ", DATA INIZIO ATTIVITA' = " + activityBeginDate
                + ", DATA FINE ATTIVITA' = " + activityEndDate + " CLIENTI PRESENTI AL MOMENTO = " + currentNumberOfPeople + ", NUMERO MASSIMO DI PERSONE = ";
        final StringBuilder stringBuilder = new StringBuilder(toString);
        if (maxNumberOfPeople < 0) {
            stringBuilder.append("NESSUN LIMITE");
        } else {
            stringBuilder.append(maxNumberOfPeople);
        }
        toString =  stringBuilder.toString();
        return toString + ", CLIENTI = " + customersInThisActivity;
    }
}
