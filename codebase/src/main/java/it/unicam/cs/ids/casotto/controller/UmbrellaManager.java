package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Umbrella;
import it.unicam.cs.ids.casotto.repository.UmbrellaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UmbrellaManager {

    private final UmbrellaRepository umbrellaRepository;

    @Autowired
    public UmbrellaManager(UmbrellaRepository umbrellaRepository) {
        this.umbrellaRepository = umbrellaRepository;
    }

    public void saveUmbrella(Umbrella umbrella) {
        this.umbrellaRepository.save(umbrella);
    }

    public Umbrella getUmbrellaById(long umbrellaId) {
        Optional<Umbrella> umbrella = this.umbrellaRepository.findById(umbrellaId);
        return umbrella.orElse(null);
    }

    public void modifyUmbrella(long umbrellaId, Double diameter, String material, Double price) {
        Umbrella umbrella = this.getUmbrellaById(umbrellaId);
        if (diameter != null) {
            umbrella.setDiameter(diameter);
        }
        if (material != null) {
            umbrella.setMaterial(material);
        }
        if (price != null) {
            umbrella.setPrice(price);
        }
        this.umbrellaRepository.save(umbrella);
    }

    public void saveUmbrella(int diameter, String material, int price) {
        this.umbrellaRepository.save(new Umbrella(diameter,material,price));
    }

    public List<Umbrella> getAllUmbrellas() {
        return umbrellaRepository.findAll();
    }
}
