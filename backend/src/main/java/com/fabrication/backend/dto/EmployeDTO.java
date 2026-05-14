package com.fabrication.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * DTO pour la création et mise à jour d'un employé.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeDTO {

    private Long id;

    @NotBlank(message = "Le nom de l'employé est obligatoire")
    private String nom;

    @NotBlank(message = "Le poste est obligatoire")
    private String poste;

    private Long machineAssigneeId;

    // Champ en lecture seule (response)
    private String machineAssigneeNom;
}
