package com.microservices.servicevoiture.services;

import com.microservices.servicevoiture.entities.Voiture;
import com.microservices.servicevoiture.repositories.VoitureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoitureService {
    @Autowired
    private VoitureRepository voitureRepository;

    public Voiture enregistrerVoiture(Voiture voiture) {
        return voitureRepository.save(voiture);
    }
}
