package com.fabrication.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Entité représentant un produit.
 */
@Entity
@Table(name = "produits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le type du produit est obligatoire")
    @Column(nullable = false)
    private String type;

    @NotNull(message = "Le stock est obligatoire")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    @Column(nullable = false)
    private Integer stock;

    @NotBlank(message = "Le fournisseur est obligatoire")
    @Column(nullable = false)
    private String fournisseur;

    @ManyToMany
    @JoinTable(
        name = "produit_composition",
        joinColumns = @JoinColumn(name = "produit_parent_id"),
        inverseJoinColumns = @JoinColumn(name = "produit_enfant_id")
    )
    @Builder.Default
    private List<Produit> composants = new ArrayList<>();
}
