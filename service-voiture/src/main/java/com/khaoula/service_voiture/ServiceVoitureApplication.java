package com.khaoula.service_voiture;

import com.khaoula.service_voiture.entities.Client;
import com.khaoula.service_voiture.entities.Voiture;
import com.khaoula.service_voiture.repositories.VoitureRepository;
import com.khaoula.service_voiture.service.ClientService;
import com.khaoula.service_voiture.service.VoitureService;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@SpringBootApplication
@EnableFeignClients
public class ServiceVoitureApplication {

    private static final Logger log = LoggerFactory.getLogger(ServiceVoitureApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ServiceVoitureApplication.class, args);
	}
    @Bean
    @ConditionalOnProperty(name = "service.voiture.init-data", havingValue = "true", matchIfMissing = true)
    CommandLineRunner initialiserBaseH2(VoitureRepository voitureRepository, ClientService clientService){
        return args -> {
            try {
                Client c1 = clientService.clientById(2L);
                Client c2 = clientService.clientById(1L);
                log.info("**************************");
                log.info("Id est : {}", c2.getId());
                log.info("Nom est : {}", c2.getNom());
                log.info("**************************");
                log.info("**************************");
                log.info("Id est : {}", c1.getId());
                log.info("Nom est : {}", c1.getNom());
                log.info("Age est : {}", c1.getAge());
                log.info("**************************");
                voitureRepository.save(new Voiture(Long.parseLong("1"), "Toyota", "A 25 333", "Corolla", 1L, c2));
                voitureRepository.save(new Voiture(Long.parseLong("2"), "Renault", "B 6 3456", "Megane", 1L, c2));
                voitureRepository.save(new Voiture(Long.parseLong("3"), "Peugeot", "A 55 4444", "301", 2L, c1));
            } catch (FeignException ex) {
                log.warn("Impossible d’initialiser la base H2: SERVICE-CLIENT indisponible ({})", ex.getMessage());
            }
        };
    }
}
