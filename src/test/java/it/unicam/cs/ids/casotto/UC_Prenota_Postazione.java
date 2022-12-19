package it.unicam.cs.ids.casotto;

import it.unicam.cs.ids.casotto.controller.CustomerManager;
import it.unicam.cs.ids.casotto.controller.ReservationManager;
import it.unicam.cs.ids.casotto.model.Customer;
import it.unicam.cs.ids.casotto.model.DiscountCode;
import it.unicam.cs.ids.casotto.model.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class UC_Prenota_Postazione {
    @Autowired
    ReservationManager reservationManager;
    CustomerManager customerManager;
    @Test
    void test1(){

        boolean repeat = true,loop=true;
        double totalPrice=0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        while(repeat) {
            System.out.println("Per favore inserisci la data per la prenotazione: ");
            Scanner in = new Scanner(System.in);
            List<Location> choices = new ArrayList<>();
            LocalDate reservationDate = LocalDate.parse(in.nextLine(),formatter);

            List<Location> availableLocations = reservationManager.getAvailableLocationsOnADate(reservationDate);
            if(!availableLocations.isEmpty()) {
                System.out.println("Ecco le postazioni disponibili per la data "+reservationDate+"\n");
                for (int i = 0; i < availableLocations.size(); i++) {
                    System.out.println(i+": "+availableLocations.get(i));
                }
                System.out.println("Scegli una delle postazioni disponibili\n");
                int choice = Integer.parseInt(in.nextLine());
                System.out.println("Il prezzo della postazione "+choice+" è: "+ availableLocations.get(choice).getPrice()+"\n");
                choices.add(availableLocations.get(choice));
                totalPrice += availableLocations.get(choice).getPrice();
//                while(loop){
//                    System.out.println("Vuoi prenotare un'altra postazione? Y/n\n");
//                    if(in.nextLine().equals("Y")) {
//                        choice = Integer.parseInt(in.nextLine());
//                        System.out.println("Il prezzo della postazione "+choice+" è: "+ availableLocations.get(choice).getPrice()+"\n");
//                        choices.add(availableLocations.get(choice));
//                        totalPrice += availableLocations.get(choice).getPrice();
//                    } else loop=false;
//                }

                System.out.println("Vuoi inserire un codice sconto? Y/n\n");
                if(in.nextLine().equals("Y")) {
                    System.out.println("Inserire il codice sconto: ");
                    DiscountCode discountCode = new DiscountCode(in.nextLine());
                    if(reservationManager.checkDiscountCode(discountCode))
                        reservationManager.applyDiscountCode(totalPrice, discountCode);
                }

                System.out.println("Inizio procedura di pagamento...\n");
                if(reservationManager.startPayment()) {
                    reservationManager.createReservation(1L,LocalDate.now(), reservationDate,reservationDate,choices.get(0),totalPrice);
                    System.out.println("Prenotazione confermata!\n");
                }
                return;
            } else {
                System.out.println("Non sono disponibili postazioni in questa data...");
                System.out.println("Vuoi inserire un'altra data? Y/n");
                if(!in.nextLine().equals("Y"))
                    repeat = false;
            }
        }
    }
}