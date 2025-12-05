# Orchestration de microservices avec Spring Cloud Eureka, Gateway et OpenFeign

Ce projet démontre l'orchestration de microservices utilisant Spring Cloud avec Eureka Server pour la découverte de services, Spring Cloud Gateway pour le routage, et OpenFeign pour la communication inter-services.

## Architecture

Le projet est composé de 4 microservices :

1. **Eureka Server** (port 8761) - Serveur de découverte de services
2. **Service Client** (port 8088) - Microservice de gestion des clients
3. **Service Voiture** (port 8089) - Microservice de gestion des voitures avec communication Feign vers Service Client
4. **Gateway** (port 8888) - API Gateway pour le routage des requêtes

## Technologies Utilisées

- Spring Boot 3.2.0
- Spring Cloud 2023.0.0
- Spring Cloud Netflix Eureka
- Spring Cloud Gateway
- Spring Cloud OpenFeign
- Spring Data JPA
- H2 Database
- Lombok
- Maven

## Prérequis

- Java 17 ou supérieur
- Maven 3.6 ou supérieur

## Installation et Démarrage

### 1. Compiler tous les modules

```bash
mvn clean install
```

### 2. Démarrer les services dans l'ordre suivant

#### a) Démarrer Eureka Server (port 8761)
```bash
cd eureka-server
mvn spring-boot:run
```

Vérifier que Eureka est disponible : http://localhost:8761/

#### b) Démarrer Service Client (port 8088)
```bash
cd Client
mvn spring-boot:run
```

#### c) Démarrer Service Voiture (port 8089)
```bash
cd service-voiture
mvn spring-boot:run
```

#### d) Démarrer Gateway (port 8888)
```bash
cd GateWay
mvn spring-boot:run
```

### 3. Vérifications

#### Vérifier Eureka Dashboard
Ouvrir http://localhost:8761/ et vérifier que SERVICE-CLIENT et SERVICE-VOITURE sont enregistrés.

#### Tester les endpoints du Service Client

**Accès direct :**
- http://localhost:8088/clients - Liste tous les clients
- http://localhost:8088/clients/1 - Récupérer le client avec ID 1

**Via Gateway (routage Eureka) :**
- http://localhost:8888/clients - Liste tous les clients
- http://localhost:8888/clients/1 - Récupérer le client avec ID 1

#### Tester les endpoints du Service Voiture

**Accès direct :**
- http://localhost:8089/voitures - Liste toutes les voitures
- http://localhost:8089/voitures/1 - Récupérer la voiture avec ID 1 (avec infos client via Feign)
- http://localhost:8089/voitures/client/1 - Liste toutes les voitures d'un client

**Via Gateway (routage Eureka) :**
- http://localhost:8888/voitures - Liste toutes les voitures
- http://localhost:8888/voitures/1 - Récupérer la voiture avec ID 1

## Configuration du Gateway

Le Gateway est configuré avec deux modes possibles :

### Mode statique avec Eureka Load Balancing (activé par défaut)
Configuration dans `GateWayApplication.java` via le bean `RouteLocator` :
- Routes configurées avec `lb://SERVICE-NAME` pour load balancing via Eureka
- Routes : `/clients/**` → SERVICE-CLIENT, `/voitures/**` → SERVICE-VOITURE

### Mode dynamique (optionnel)
Pour activer le routage dynamique automatique :
1. Commenter le bean `RouteLocator` dans `GateWayApplication.java`
2. Décommenter le bean `DiscoveryClientRouteDefinitionLocator`
3. Redémarrer Gateway

Accès avec routage dynamique :
- http://localhost:8888/SERVICE-CLIENT/clients
- http://localhost:8888/SERVICE-VOITURE/voitures

## Données de Test

### Service Client
Au démarrage, 3 clients sont créés automatiquement :
- ID 1 : Rabab SELIMANI, 23 ans
- ID 2 : Amal RAMI, 22 ans
- ID 3 : Samir SAFI, 22 ans

### Service Voiture
Au démarrage, 3 voitures sont créées automatiquement :
- ID 1 : Toyota Corolla (A 25 333) - Client ID 1
- ID 2 : Renault Megane (B 6 3456) - Client ID 1
- ID 3 : Peugeot 301 (A 55 4444) - Client ID 2

## API Endpoints

### Service Client (port 8088)
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | /clients | Liste tous les clients |
| GET | /clients/{id} | Récupère un client par ID |

### Service Voiture (port 8089)
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | /voitures | Liste toutes les voitures |
| GET | /voitures/{id} | Récupère une voiture par ID avec infos client |
| GET | /voitures/client/{id} | Liste les voitures d'un client |
| POST | /voitures/{clientId} | Crée une nouvelle voiture pour un client |
| PUT | /voitures/{id} | Met à jour une voiture |

### Gateway (port 8888)
Toutes les routes ci-dessus sont accessibles via le Gateway en utilisant les mêmes chemins.

## Structure du Projet

```
.
├── eureka-server/          # Serveur de découverte Eureka
├── Client/                 # Microservice de gestion des clients
│   ├── entities/          # Entités JPA
│   ├── repositories/      # Repositories Spring Data
│   └── controllers/       # Contrôleurs REST
├── service-voiture/       # Microservice de gestion des voitures
│   ├── entities/          # Entités JPA
│   ├── models/            # POJOs (Client)
│   ├── repositories/      # Repositories Spring Data
│   ├── services/          # Services et Feign clients
│   └── web/               # Contrôleurs REST
└── GateWay/               # API Gateway
```

## Communication Inter-Services

Le Service Voiture communique avec le Service Client via OpenFeign :
- Interface `ClientService` annotée avec `@FeignClient(name="SERVICE-CLIENT")`
- Découverte automatique du Service Client via Eureka
- Load balancing automatique si plusieurs instances du Service Client

## Notes Importantes

1. **Ordre de démarrage** : Démarrer Eureka Server en premier, puis les autres services
2. **Temps de découverte** : Attendre 30 secondes après le démarrage de chaque service pour qu'il s'enregistre dans Eureka
3. **Base de données** : H2 en mémoire - les données sont perdues au redémarrage
4. **Ports** : Assurez-vous que les ports 8761, 8088, 8089, et 8888 sont disponibles

## Troubleshooting

### Service non visible dans Eureka
- Vérifier que Eureka Server est démarré
- Attendre 30 secondes pour l'enregistrement
- Vérifier les logs du service

### Erreur Feign lors du démarrage de Service Voiture
- S'assurer que Service Client est démarré et enregistré dans Eureka
- Vérifier la configuration Eureka dans application.properties

### Gateway ne route pas correctement
- Vérifier que les services sont enregistrés dans Eureka
- Vérifier la configuration des routes dans GateWayApplication.java

## Auteur

Projet de démonstration - Orchestration de microservices avec Spring Cloud