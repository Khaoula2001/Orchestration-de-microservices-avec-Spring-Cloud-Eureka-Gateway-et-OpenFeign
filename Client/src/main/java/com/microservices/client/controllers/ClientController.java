package com.microservices.client.controllers;

import com.microservices.client.entities.Client;
import com.microservices.client.exceptions.ClientNotFoundException;
import com.microservices.client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    public Client findById(@PathVariable Long id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new ClientNotFoundException(id));
    }
}
