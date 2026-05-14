package com.fabrication.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Entité représentant un employé.
 */
@Entity
@Table(name = "employes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le poste est obligatoire")
    @Column(nullable = false)
    private String poste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_assignee_id")
    private Machine machineAssignee;
}
