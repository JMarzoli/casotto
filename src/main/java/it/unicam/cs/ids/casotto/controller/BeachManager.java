package it.unicam.cs.ids.casotto.controller;

import it.unicam.cs.ids.casotto.model.Beach;
import it.unicam.cs.ids.casotto.repository.BeachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BeachManager {

    private final BeachRepository beachRepository;

    @Autowired
    public BeachManager(BeachRepository beachRepository) {
        this.beachRepository = beachRepository;
    }

    public List<Beach> findAllBeaches() {
        return this.beachRepository.findAll();
    }

    public Beach getBeachById(Long beachId) {
        Optional<Beach> findBeach = this.beachRepository.findById(beachId);
        return findBeach.orElse(null);
    }

    public void saveBeach(Beach beach) {
        this.beachRepository.save(beach);
    }

    public void updateModifyBeach(Long beachId, String sandType, String description) {
        Beach beach = getBeachById(beachId);
        if (sandType != null) {
            beach.setSandType(sandType);
        }
        if (description != null) {
            beach.setDescription(description);
        }
        this.beachRepository.save(beach);
    }
}
