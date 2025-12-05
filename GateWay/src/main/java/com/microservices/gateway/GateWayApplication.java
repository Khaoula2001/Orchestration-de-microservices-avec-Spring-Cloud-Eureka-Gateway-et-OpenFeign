package com.microservices.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

    // Configuration statique avec Eureka - Load Balancing
    @Bean
    RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/clients/**").uri("lb://SERVICE-CLIENT"))
            .route(r -> r.path("/voitures/**").uri("lb://SERVICE-VOITURE"))
            .build();
    }

    // Configuration dynamique - découverte automatique des services
    // Décommenter cette méthode pour activer le routage dynamique
    // Commenter le bean RouteLocator ci-dessus si vous utilisez cette configuration
    /*
    @Bean
    DiscoveryClientRouteDefinitionLocator routesDynamique(
            ReactiveDiscoveryClient rdc, DiscoveryLocatorProperties dlp) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }
    */
}
