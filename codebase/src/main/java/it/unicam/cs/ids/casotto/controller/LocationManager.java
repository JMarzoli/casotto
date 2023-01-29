package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.*;
import it.unicam.cs.ids.casotto.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class LocationManager {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationManager(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location createLocation(String desc) {
        return locationRepository.save(new Location(desc));
    }

    public void addUmbrellaToLocation(Long locationId, Umbrella umbrella) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(NullPointerException::new);
        location.setUmbrellas(List.of(umbrella));
        this.locationRepository.save(location);
    }

    public void addBeachChairToLocation(Long locationId, BeachChair beachChair) {
        Location location = this.locationRepository.findById(locationId).orElseThrow(NullPointerException::new);
        location.setBeachChairs(List.of(beachChair));
        this.locationRepository.save(location);
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

    public Location findByQrCode(String qrcode) {
        return new Location();
    }
}
