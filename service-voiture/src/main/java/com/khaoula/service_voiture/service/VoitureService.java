package com.khaoula.service_voiture.service;

import com.khaoula.service_voiture.entities.Voiture;
import com.khaoula.service_voiture.repositories.VoitureRepository;
import org.springframework.stereotype.Service;

@Service
public class VoitureService {

    private final VoitureRepository voitureRepository;

    public VoitureService(VoitureRepository voitureRepository) {
        this.voitureRepository = voitureRepository;
    }

    public Voiture enregistrerVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }
}

