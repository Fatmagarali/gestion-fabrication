package com.fabrication.backend.controller;

import com.fabrication.backend.dto.MachineDTO;
import com.fabrication.backend.entity.EtatMachine;
import com.fabrication.backend.service.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/machines")
@RequiredArgsConstructor
@Tag(name = "Machines", description = "Gestion des machines de production")
public class MachineController {

    private final MachineService machineService;

    @GetMapping
    @Operation(summary = "Récupérer toutes les machines")
    public ResponseEntity<List<MachineDTO>> getAllMachines() {
        return ResponseEntity.ok(machineService.getAllMachines());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une machine par ID")
    public ResponseEntity<MachineDTO> getMachineById(@PathVariable Long id) {
        return ResponseEntity.ok(machineService.getMachineById(id));
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle machine")
    public ResponseEntity<MachineDTO> createMachine(@Valid @RequestBody MachineDTO dto) {
        MachineDTO created = machineService.createMachine(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une machine")
    public ResponseEntity<MachineDTO> updateMachine(@PathVariable Long id, @Valid @RequestBody MachineDTO dto) {
        return ResponseEntity.ok(machineService.updateMachine(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une machine")
    public ResponseEntity<Void> deleteMachine(@PathVariable Long id) {
        machineService.deleteMachine(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/etat/{etat}")
    @Operation(summary = "Filtrer les machines par état")
    public ResponseEntity<List<MachineDTO>> getByEtat(@PathVariable EtatMachine etat) {
        return ResponseEntity.ok(machineService.getByEtat(etat));
    }

    @PutMapping("/{id}/maintenance")
    @Operation(summary = "Enregistrer une maintenance pour une machine")
    public ResponseEntity<MachineDTO> enregistrerMaintenance(@PathVariable Long id) {
        return ResponseEntity.ok(machineService.enregistrerMaintenance(id));
    }

    @PatchMapping("/{id}/etat")
    @Operation(summary = "Changer l'état d'une machine")
    public ResponseEntity<MachineDTO> changerEtat(@PathVariable Long id, @RequestParam EtatMachine etat) {
        return ResponseEntity.ok(machineService.changerEtat(id, etat));
    }
}
