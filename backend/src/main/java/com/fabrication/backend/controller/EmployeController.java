package com.fabrication.backend.controller;

import com.fabrication.backend.dto.EmployeDTO;
import com.fabrication.backend.service.EmployeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
@Tag(name = "Employés", description = "Gestion des employés")
public class EmployeController {

    private final EmployeService employeService;

    @GetMapping
    @Operation(summary = "Récupérer tous les employés")
    public ResponseEntity<List<EmployeDTO>> getAllEmployes() {
        return ResponseEntity.ok(employeService.getAllEmployes());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un employé par ID")
    public ResponseEntity<EmployeDTO> getEmployeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeService.getEmployeById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel employé")
    public ResponseEntity<EmployeDTO> createEmploye(@Valid @RequestBody EmployeDTO dto) {
        EmployeDTO created = employeService.createEmploye(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un employé")
    public ResponseEntity<EmployeDTO> updateEmploye(@PathVariable Long id, @Valid @RequestBody EmployeDTO dto) {
        return ResponseEntity.ok(employeService.updateEmploye(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un employé")
    public ResponseEntity<Void> deleteEmploye(@PathVariable Long id) {
        employeService.deleteEmploye(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{employeId}/assigner/{machineId}")
    @Operation(summary = "Affecter un employé à une machine")
    public ResponseEntity<EmployeDTO> assignerMachine(@PathVariable Long employeId, @PathVariable Long machineId) {
        return ResponseEntity.ok(employeService.assignerMachine(employeId, machineId));
    }

    @PutMapping("/{employeId}/desassigner")
    @Operation(summary = "Désaffecter un employé de sa machine")
    public ResponseEntity<EmployeDTO> desassignerMachine(@PathVariable Long employeId) {
        return ResponseEntity.ok(employeService.desassignerMachine(employeId));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Récupérer les employés non affectés à une machine")
    public ResponseEntity<List<EmployeDTO>> getEmployesDisponibles() {
        return ResponseEntity.ok(employeService.getEmployesDisponibles());
    }

    @GetMapping("/machine/{machineId}")
    @Operation(summary = "Récupérer les employés affectés à une machine")
    public ResponseEntity<List<EmployeDTO>> getEmployesByMachine(@PathVariable Long machineId) {
        return ResponseEntity.ok(employeService.getEmployesByMachine(machineId));
    }
}
