package com.fabrication.backend.config;

import com.fabrication.backend.entity.*;
import com.fabrication.backend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * Initialise des données de démonstration au démarrage de l'application.
 * Actif uniquement si la base est vide.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProduitRepository produitRepository;
    private final MachineRepository machineRepository;
    private final EmployeRepository employeRepository;
    private final OrdreFabricationRepository ordreFabricationRepository;

    @Override
    public void run(String... args) {
        if (produitRepository.count() > 0) {
            log.info("Base de données déjà initialisée, skip.");
            return;
        }

        log.info("Initialisation des données de démonstration...");

        // --- Produits ---
        Produit p1 = Produit.builder().nom("Plaque Acier 10mm").type("Matière première").stock(500).fournisseur("AcierPlus SA").build();
        Produit p2 = Produit.builder().nom("Boulon M8").type("Composant").stock(10000).fournisseur("FixPro SARL").build();
        Produit p3 = Produit.builder().nom("Moteur Électrique 5kW").type("Composant").stock(50).fournisseur("ElecMotor Inc.").build();
        Produit p4 = Produit.builder().nom("Châssis Assemblé").type("Produit fini").stock(20).fournisseur("Interne").build();
        
        // --- Nouveaux produits (Exemple Détergent) ---
        Produit p5 = Produit.builder().nom("Eau Purifiée (L)").type("Matière première").stock(5000).fournisseur("AquaDist SA").build();
        Produit p6 = Produit.builder().nom("Savon Liquide Brut (L)").type("Matière première").stock(1000).fournisseur("ChemCorp").build();
        Produit p7 = Produit.builder().nom("Granulés Plastique (kg)").type("Matière première").stock(2000).fournisseur("PlastX").build();
        
        Produit p8 = Produit.builder().nom("Bouteille Vide 1L").type("Composant").stock(300).fournisseur("Interne").build();
        Produit p9 = Produit.builder().nom("Bouchon Doseur").type("Composant").stock(500).fournisseur("PlastX").build();
        Produit p10 = Produit.builder().nom("Étiquette 'SuperClean'").type("Composant").stock(1500).fournisseur("PrintPro").build();
        
        Produit p11 = Produit.builder().nom("Détergent SuperClean 1L").type("Produit fini").stock(150).fournisseur("Interne").build();
        Produit p12 = Produit.builder().nom("Lessive Liquide 2L").type("Produit fini").stock(45).fournisseur("Interne").build();
        
        produitRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12));

        // --- Machines ---
        Machine m1 = Machine.builder()
                .nom("Tour CNC Alpha")
                .etat(EtatMachine.DISPONIBLE)
                .derniereMaintenance(LocalDate.of(2025, 4, 15))
                .build();
        Machine m2 = Machine.builder()
                .nom("Fraiseuse Beta")
                .etat(EtatMachine.EN_MARCHE)
                .derniereMaintenance(LocalDate.of(2025, 3, 20))
                .build();
        Machine m3 = Machine.builder()
                .nom("Presse Hydraulique Gamma")
                .etat(EtatMachine.EN_MAINTENANCE)
                .derniereMaintenance(LocalDate.of(2025, 5, 1))
                .build();
        Machine m4 = Machine.builder()
                .nom("Robot Soudeur Delta")
                .etat(EtatMachine.DISPONIBLE)
                .derniereMaintenance(LocalDate.of(2025, 2, 10))
                .build();
        machineRepository.saveAll(Arrays.asList(m1, m2, m3, m4));

        // --- Employés ---
        Employe e1 = Employe.builder()
                .nom("Ahmed Benali")
                .poste("Opérateur CNC")
                .machineAssignee(m1)
                .build();
        Employe e2 = Employe.builder()
                .nom("Fatima Zahra")
                .poste("Technicienne Maintenance")
                .machineAssignee(m3)
                .build();
        Employe e3 = Employe.builder()
                .nom("Karim Mansouri")
                .poste("Opérateur Soudure")
                .machineAssignee(null)
                .build();
        Employe e4 = Employe.builder()
                .nom("Sara Idrissi")
                .poste("Ingénieure Production")
                .machineAssignee(m2)
                .build();
        employeRepository.saveAll(Arrays.asList(e1, e2, e3, e4));

        // --- Ordres de Fabrication ---
        OrdreFabrication of1 = OrdreFabrication.builder()
                .projet("Projet Alpha - Châssis")
                .produit(p4)
                .quantite(100)
                .date(LocalDate.of(2025, 5, 10))
                .etat(EtatOrdre.EN_COURS)
                .machine(m2)
                .employes(Arrays.asList(e1, e4))
                .build();
        OrdreFabrication of2 = OrdreFabrication.builder()
                .projet("Commande Client X")
                .produit(p1)
                .quantite(250)
                .date(LocalDate.of(2025, 5, 15))
                .etat(EtatOrdre.EN_ATTENTE)
                .machine(m1)
                .employes(Arrays.asList(e3))
                .build();
        OrdreFabrication of3 = OrdreFabrication.builder()
                .projet("Maintenance Interne")
                .produit(p3)
                .quantite(10)
                .date(LocalDate.of(2025, 4, 28))
                .etat(EtatOrdre.TERMINE)
                .machine(m3)
                .employes(Arrays.asList(e2))
                .build();
        ordreFabricationRepository.saveAll(Arrays.asList(of1, of2, of3));

        log.info("Données de démonstration chargées avec succès !");
    }
}
