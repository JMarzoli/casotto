package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.*;
import it.unicam.cs.ids.casotto.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class represents the manager of the reservations.
 */
@Service
public class ReservationManager {
    private final ReservationRepository reservationRepository;
    private final LocationManager locationManager;
    private final CustomerManager customerManager;

    @Autowired
    public ReservationManager(ReservationRepository reservationRepository, LocationManager locationManager,CustomerManager customerManager) {
        this.reservationRepository = reservationRepository;
        this.locationManager = locationManager;
        this.customerManager = customerManager;
    }

    public void makeReservation(Customer customer, Long location, LocalDate startDate, LocalDate endDate, double reservationPrice) {
        LocalDate reservationDate = LocalDate.now();
        reservationRepository.save(new Reservation(customer, reservationDate, startDate, endDate, locationManager.findById(location).orElse(null), reservationPrice));
    }

    /**
     * This method returns a location from a specific Id.
     * @param locationId to find;
     * @return the Location.
     */
    public Location selectLocationById(Long locationId) {
        Optional<Location> loc = locationManager.findById(locationId);
        return loc.orElse(null);
    }

    /**
     * This method returns the price of a specific Location
     * @param locationId of the location to get the price;
     * @return the price of that location.
     */
    public double getPriceLocationById(Long locationId) {
        Optional<Location> loc = locationManager.findById(locationId);
        double totalCost = 0;
        if(loc.isPresent()) {
            Collection<Umbrella> umbrellas = loc.get().getUmbrellas();
            for (Umbrella u: umbrellas) {
                totalCost += u.getPrice();
            }
        } else totalCost = -1;
        return totalCost;
    }

    /**
     * This method returns the price of the reservation after applying a discount code.
     *
     * @param price
     * @param discountCode to use;
     * @return the price discounted.
     */
    public double applyDiscountCode(Double price, DiscountCode discountCode) {
        return price/2;
    }

    /**
     * This method returns the result of the payment.
     * @return true if successful, false otherwise.
     */
    public boolean startPayment() {
        System.out.println("Per favore inserisci il titolare della carta");
        Scanner in = new Scanner(System.in);
        String cardOwner = in.nextLine();
        System.out.println("Per favore inserisci il numero della carta");
        String cardNumber = in.nextLine();
        System.out.println("Per favore inserisci la data di scadenza");
        String expiryDate = in.nextLine();
        System.out.println("Per favore inserisci il cvv");
        int cvv = in.nextInt();
        System.out.println("Perfetto, effettuo il pagamento...");
        System.out.println("Pagamento effettuato con successo!");
        return true;
    }

    /**
     * This method returns all the Locations available on a specific Date.
     * @return all the Locations available.
     */
    public List<Location> getAvailableLocationsOnADate(LocalDate reservationStartDate,LocalDate reservationEndDate) {
        List<Location> locationsAlreadyOccupied = reservationRepository.findAll()
                .stream()
                .filter(reserved -> reserved.getReservationBeginDate().isBefore(reservationEndDate)&&reserved.getReservationEndDate().isAfter(reservationStartDate))
                .map(Reservation::getLocationReserved)
                .toList();
        List<Location> locations = locationManager.getAllLocations();
        locations.removeAll(locationsAlreadyOccupied);
        return locations;
    }

    public boolean checkDiscountCode(DiscountCode discountCode) {
        return true;
    }

    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }
}
