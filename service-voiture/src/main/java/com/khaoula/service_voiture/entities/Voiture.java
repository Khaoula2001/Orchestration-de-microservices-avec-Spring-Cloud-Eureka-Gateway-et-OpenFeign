package com.khaoula.service_voiture.entities;

import com.khaoula.service_voiture.entities.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voiture {

    @Id
    @GeneratedValue
    private Long id;
    private String marque;
    private String matricule;
    private String model;
    private Long clientId;

    @Transient
    private Client client;
}
