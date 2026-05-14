package com.fabrication.backend.dto;

import com.fabrication.backend.entity.EtatOrdre;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO pour la création et mise à jour d'un ordre de fabrication.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdreFabricationDTO {

    private Long id;

    @NotBlank(message = "Le projet est obligatoire")
    private String projet;

    @NotNull(message = "L'ID du produit est obligatoire")
    private Long produitId;

    // Champ en lecture seule (response)
    private String produitNom;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private Integer quantite;

    @NotNull(message = "La date est obligatoire")
    private LocalDate date;

    @NotNull(message = "L'état est obligatoire")
    private EtatOrdre etat;
}
