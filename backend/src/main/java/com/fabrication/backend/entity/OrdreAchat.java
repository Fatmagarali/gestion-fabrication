package com.fabrication.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "ordres_achat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdreAchat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @NotNull
    @Min(1)
    private Integer quantite;

    @NotNull
    private LocalDate dateAchat;

    @NotBlank
    private String fournisseur;

    @Builder.Default
    private String statut = "EN_ATTENTE"; // EN_ATTENTE, RECU
}
