package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.BeachChair;
import it.unicam.cs.ids.casotto.model.Location;
import it.unicam.cs.ids.casotto.model.Reservation;
import it.unicam.cs.ids.casotto.model.Umbrella;
import it.unicam.cs.ids.casotto.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LocationManager {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationManager(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(Long id,String desc) {
        return locationRepository.save(new Location(id,desc));
    }
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Optional<Location> findById(Long locationId) {
        return locationRepository.findById(locationId);
    }

    public double getTotalPrice(Long locationId) {
        Optional<Location> loc = locationRepository.findById(locationId);
        double totalCost = 0;
        for (Umbrella u: loc.get().getUmbrellas()) {
            totalCost += u.getPrice();
        }
        for (BeachChair b: loc.get().getBeachChairs()) {
            totalCost += b.getPrice();
        }
        return totalCost;
    }

    public void modifyPriceLocationUmbrellaById(Long locationId, double newPrice) {
        Optional<Location> loc = locationRepository.findById(locationId);
        loc.ifPresent(location -> location.getUmbrellas().forEach(umbrella -> umbrella.setPrice(newPrice)));
    }
    public void modifyPriceLocationBeachChairById(Long locationId, double newPrice) {
        Optional<Location> loc = locationRepository.findById(locationId);
        loc.ifPresent(location -> location.getBeachChairs().forEach(beachChair -> beachChair.setPrice(newPrice)));
    }
}
