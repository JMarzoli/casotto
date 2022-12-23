package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.BeachChair;
import it.unicam.cs.ids.casotto.repository.BeachChairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeachChairManager {

    private final BeachChairRepository beachChairRepository;

    @Autowired
    public BeachChairManager(BeachChairRepository beachChairRepository) {
        this.beachChairRepository = beachChairRepository;
    }

    public void saveBeachChair(BeachChair beachChair) {
        this.beachChairRepository.save(beachChair);
    }

    public BeachChair getBeachChairById(long beachChairId) {
        Optional<BeachChair> beachChair = this.beachChairRepository.findById(beachChairId);
        return beachChair.orElse(null);
    }

    public void modifyBeachChair(long beachChairId, String material, Double length, Double width, Double price) {
        BeachChair beachChair = this.getBeachChairById(beachChairId);
        if (material != null) {
            beachChair.setMaterial(material);
        }
        if (length != null) {
            beachChair.setLength(length);
        }
        if (width != null) {
            beachChair.setWidth(width);
        }
        if (price != null) {
            beachChair.setPrice(price);
        }
        this.beachChairRepository.save(beachChair);
    }
}
