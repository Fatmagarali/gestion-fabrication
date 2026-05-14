package com.fabrication.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Entité représentant une machine de production.
 */
@Entity
@Table(name = "machines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de la machine est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotNull(message = "L'état de la machine est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatMachine etat;

    @Column(name = "derniere_maintenance")
    private LocalDate derniereMaintenance;
}
