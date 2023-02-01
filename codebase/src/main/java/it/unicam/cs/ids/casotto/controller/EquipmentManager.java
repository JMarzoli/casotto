package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Equipment;
import it.unicam.cs.ids.casotto.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentManager {

    private final EquipmentRepository equipmentRepository;

    @Autowired
    public EquipmentManager(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public void saveEquipment(Equipment equipment) {
        this.equipmentRepository.save(equipment);
    }

    public List<Equipment> equipmentList() {
        return this.equipmentRepository.findAll();
    }

    public Equipment getEquipmentById(long equipmentId) {
        Optional<Equipment> equipment = this.equipmentRepository.findById(equipmentId);
        return equipment.orElse(null);
    }

    public void modifyEquipment(long equipmentId, String name, String description) {
        Equipment equipment = this.getEquipmentById(equipmentId);
        if (name != null) {
            equipment.setName(name);
        }
        if (description != null) {
            equipment.setDescription(description);
        }
        this.equipmentRepository.save(equipment);
    }

    public List<Equipment> getAllEquipments() {
        return equipmentRepository.findAll();
    }
}
