package com.khaoula.Client.Controllers;

import com.khaoula.Client.entities.Client;
import com.khaoula.Client.repositories.ClientRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @GetMapping("/client/{id}")
    public Client findById(@PathVariable Long id) throws Exception {
        return clientRepository.findById(id)
                .orElseThrow(() -> new Exception("Client non trouvé"));
    }
}
