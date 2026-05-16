package com.fabrication.backend.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO pour la création et mise à jour d'un produit.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProduitDTO {

    private Long id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    private String nom;

    @NotBlank(message = "Le type du produit est obligatoire")
    private String type;

    @NotNull(message = "Le stock est obligatoire")
    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private Integer stock;

    @NotBlank(message = "Le fournisseur est obligatoire")
    private String fournisseur;

    private java.util.List<Long> composantsIds;
    private java.util.List<String> composantsNoms;
}
