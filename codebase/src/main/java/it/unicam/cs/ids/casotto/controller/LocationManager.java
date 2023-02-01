package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.*;
import it.unicam.cs.ids.casotto.repository.BeachChairRepository;
import it.unicam.cs.ids.casotto.repository.LocationRepository;
import it.unicam.cs.ids.casotto.repository.UmbrellaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
@Service
public class LocationManager {
    private final LocationRepository locationRepository;

    private final UmbrellaRepository umbrellaRepository;

    private final BeachChairRepository beachChairRepository;

    @Autowired
    public LocationManager(LocationRepository locationRepository, UmbrellaRepository umbrellaRepository, BeachChairRepository beachChairRepository) {
        this.locationRepository = locationRepository;
        this.umbrellaRepository = umbrellaRepository;
        this.beachChairRepository = beachChairRepository;
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
        if(loc.isPresent()) {
            for (Umbrella u : loc.get().getUmbrellas()) {
                totalCost += u.getPrice();
            }
            for (BeachChair b : loc.get().getBeachChairs()) {
                totalCost += b.getPrice();
            }
        }
        return totalCost;
    }

    public void modifyPriceLocationUmbrellaById(Long locationId, double newPrice) {
        Optional<Location> loc = locationRepository.findById(locationId);
        if (loc.isPresent()) {
            Location location = loc.get();
            location.getUmbrellas().forEach(umbrella -> {
                umbrella.setPrice(newPrice);
                umbrellaRepository.save(umbrella);
            });
        }
    }
    public void modifyPriceLocationBeachChairById(Long locationId, double newPrice) {
        Optional<Location> loc = locationRepository.findById(locationId);
        if (loc.isPresent()) {
            Location location = loc.get();
            location.getBeachChairs().forEach(beachChair -> {
                beachChair.setPrice(newPrice);
                beachChairRepository.save(beachChair);
            });
        }
    }

    public Location findByQrCode(String qrcode) {
        return this.locationRepository.findAll().stream().filter(l->l.getQrCode().equals(qrcode)).findFirst().orElse(null);
    }

    public void saveLocation(String desc,List<Umbrella> umbrellas, List<BeachChair> beachSeats, String qrCode) {
        Location location = new Location();
        location.setDesc(desc);
        location.setQrCode(qrCode);
        location.setBeachChairs(beachSeats);
        location.setUmbrellas(umbrellas);
        umbrellas.forEach(umbrella -> umbrella.setLocation(location));
        beachSeats.forEach(beachChair -> beachChair.setLocation(location));
        this.locationRepository.save(location);
        umbrellas.forEach(umbrella -> umbrellaRepository.save(umbrella));
        beachSeats.forEach(beachChair -> beachChairRepository.save(beachChair));
    }
}
