package com.fabrication.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * Entité représentant un ordre de fabrication.
 */
@Entity
@Table(name = "ordres_fabrication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdreFabrication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le projet est obligatoire")
    @Column(nullable = false)
    private String projet;

    @NotNull(message = "Le produit est obligatoire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    @Column(nullable = false)
    private Integer quantite;

    @NotNull(message = "La date est obligatoire")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "L'état est obligatoire")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatOrdre etat;
}
