# Gestion des Ordres de Fabrication

Application web complète pour la gestion des ordres de fabrication, développée avec **Spring Boot** (Back-end API REST) et **Angular** (Front-end).

## Description du Projet

Ce projet permet de :
- **Créer et suivre** les ordres de fabrication (création, suivi d'état, filtrage)
- **Gérer les produits** (CRUD complet, recherche, filtrage par type)
- **Gérer les machines** (CRUD, suivi des maintenances, gestion des disponibilités)
- **Gérer les employés** (CRUD, affectation/désaffectation aux machines)

## Technologies Utilisées

### Back-end
| Technologie | Version | Description |
|---|---|---|
| Java | 17 | Langage de programmation |
| Spring Boot | 3.4.5 | Framework principal |
| Spring Data JPA | 3.4.x | Accès aux données |
| Spring Validation | 3.4.x | Validation des données |
| MySQL | 8.0 | Base de données |
| Cloud SQL (Google) | - | Base de données managée (production) |
| Lombok | - | Réduction du code boilerplate |
| SpringDoc OpenAPI | 2.8.6 | Documentation Swagger |
| Docker | - | Conteneurisation |

### Front-end
| Technologie | Description |
|---|---|
| Angular | Framework UI |

## Structure du Projet

```
gestion-fabrication/
├── backend/
│   └── src/main/java/com/fabrication/backend/
│       ├── config/          # Configuration (CORS, Swagger, DataInit)
│       ├── controller/      # Contrôleurs REST
│       ├── dto/             # Objets de transfert de données
│       ├── entity/          # Entités JPA
│       ├── exception/       # Gestion des erreurs
│       ├── repository/      # Interfaces JPA Repository
│       └── service/         # Logique métier
├── frontend/                # Application Angular
├── docker-compose.yml       # Déploiement Docker
└── README.md
```

## Entités

- **OrdreFabrication** : id, projet, produit, quantité, date, état (EN_ATTENTE, EN_COURS, TERMINE, ANNULE)
- **Produit** : id, nom, type, stock, fournisseur
- **Machine** : id, nom, état (DISPONIBLE, EN_MARCHE, EN_PANNE, EN_MAINTENANCE), dernière_maintenance
- **Employé** : id, nom, poste, machine_assignée

## Instructions d'Installation et d'Exécution

### Prérequis
- Java 17+
- Maven 3.8+ (ou utiliser le wrapper `mvnw` inclus)
- Docker & Docker Compose (pour le déploiement)

### Lancement en développement (H2)

```bash
cd backend
./mvnw spring-boot:run
```

L'application démarre sur `http://localhost:8080` avec une base H2 en mémoire.

- **Swagger UI** : http://localhost:8080/swagger-ui.html
- **Console H2** : http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:fabricationdb`)
- **API Docs** : http://localhost:8080/api-docs

### Lancement avec Docker (MySQL)

```bash
docker-compose up --build
```

Cela démarre :
- MySQL 8.0 sur le port **3307**
- Spring Boot API sur le port **8080**

### Arrêter Docker

```bash
docker-compose down
```
## Liens de Déploiement (Production)

L'application est déployée sur **Google Cloud Run** :

| Service | URL |
|---|---|
| Frontend (Angular) | https://fabrication-frontend-62780203004.europe-west1.run.app |
| Backend (API REST) | https://fabrication-backend-62780203004.europe-west1.run.app |
| Swagger UI | https://fabrication-backend-62780203004.europe-west1.run.app/swagger-ui.html |
| API Docs | https://fabrication-backend-62780203004.europe-west1.run.app/api-docs |

### Infrastructure Cloud (GCP)
| Composant | Service GCP |
|---|---|
| Backend | Cloud Run |
| Frontend | Cloud Run |
| Base de données | Cloud SQL (MySQL 8.0) |
| Registry Docker | Artifact Registry |
## Endpoints API

### Produits (`/api/produits`)
| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/produits` | Liste tous les produits |
| GET | `/api/produits/{id}` | Récupère un produit |
| POST | `/api/produits` | Crée un produit |
| PUT | `/api/produits/{id}` | Met à jour un produit |
| DELETE | `/api/produits/{id}` | Supprime un produit |
| GET | `/api/produits/search?nom=` | Recherche par nom |
| GET | `/api/produits/type/{type}` | Filtre par type |

### Machines (`/api/machines`)
| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/machines` | Liste toutes les machines |
| GET | `/api/machines/{id}` | Récupère une machine |
| POST | `/api/machines` | Crée une machine |
| PUT | `/api/machines/{id}` | Met à jour une machine |
| DELETE | `/api/machines/{id}` | Supprime une machine |
| GET | `/api/machines/etat/{etat}` | Filtre par état |
| PUT | `/api/machines/{id}/maintenance` | Enregistre une maintenance |

### Employés (`/api/employes`)
| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/employes` | Liste tous les employés |
| GET | `/api/employes/{id}` | Récupère un employé |
| POST | `/api/employes` | Crée un employé |
| PUT | `/api/employes/{id}` | Met à jour un employé |
| DELETE | `/api/employes/{id}` | Supprime un employé |
| PUT | `/api/employes/{id}/assigner/{machineId}` | Affecte à une machine |
| PUT | `/api/employes/{id}/desassigner` | Désaffecte d'une machine |
| GET | `/api/employes/disponibles` | Employés non affectés |
| GET | `/api/employes/machine/{machineId}` | Employés par machine |

### Ordres de Fabrication (`/api/ordres`)
| Méthode | Endpoint | Description |
|---|---|---|
| GET | `/api/ordres` | Liste tous les ordres |
| GET | `/api/ordres/{id}` | Récupère un ordre |
| POST | `/api/ordres` | Crée un ordre |
| PUT | `/api/ordres/{id}` | Met à jour un ordre |
| DELETE | `/api/ordres/{id}` | Supprime un ordre |
| PATCH | `/api/ordres/{id}/etat?etat=` | Change l'état (suivi) |
| GET | `/api/ordres/etat/{etat}` | Filtre par état |
| GET | `/api/ordres/periode?debut=&fin=` | Filtre par période |
| GET | `/api/ordres/search?projet=` | Recherche par projet |
