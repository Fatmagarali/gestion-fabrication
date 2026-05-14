package com.fabrication.backend.dto;

import com.fabrication.backend.entity.EtatMachine;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

/**
 * DTO pour la création et mise à jour d'une machine.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MachineDTO {

    private Long id;

    @NotBlank(message = "Le nom de la machine est obligatoire")
    private String nom;

    @NotNull(message = "L'état de la machine est obligatoire")
    private EtatMachine etat;

    private LocalDate derniereMaintenance;
}
