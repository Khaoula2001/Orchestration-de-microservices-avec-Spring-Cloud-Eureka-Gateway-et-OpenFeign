package com.microservices.servicevoiture.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VoitureNotFoundException extends RuntimeException {
    public VoitureNotFoundException(Long id) {
        super("Voiture non trouvée avec l'ID: " + id);
    }
}
