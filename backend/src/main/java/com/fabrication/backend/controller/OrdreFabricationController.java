package com.fabrication.backend.controller;

import com.fabrication.backend.dto.OrdreFabricationDTO;
import com.fabrication.backend.entity.EtatOrdre;
import com.fabrication.backend.service.OrdreFabricationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ordres")
@RequiredArgsConstructor
@Tag(name = "Ordres de Fabrication", description = "Création et suivi des ordres de fabrication")
public class OrdreFabricationController {

    private final OrdreFabricationService ordreFabricationService;

    @GetMapping
    @Operation(summary = "Récupérer tous les ordres de fabrication")
    public ResponseEntity<List<OrdreFabricationDTO>> getAllOrdres() {
        return ResponseEntity.ok(ordreFabricationService.getAllOrdres());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un ordre de fabrication par ID")
    public ResponseEntity<OrdreFabricationDTO> getOrdreById(@PathVariable Long id) {
        return ResponseEntity.ok(ordreFabricationService.getOrdreById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouvel ordre de fabrication")
    public ResponseEntity<OrdreFabricationDTO> createOrdre(@Valid @RequestBody OrdreFabricationDTO dto) {
        OrdreFabricationDTO created = ordreFabricationService.createOrdre(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un ordre de fabrication")
    public ResponseEntity<OrdreFabricationDTO> updateOrdre(@PathVariable Long id, @Valid @RequestBody OrdreFabricationDTO dto) {
        return ResponseEntity.ok(ordreFabricationService.updateOrdre(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un ordre de fabrication")
    public ResponseEntity<Void> deleteOrdre(@PathVariable Long id) {
        ordreFabricationService.deleteOrdre(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/etat")
    @Operation(summary = "Changer l'état d'un ordre de fabrication (suivi)")
    public ResponseEntity<OrdreFabricationDTO> changerEtat(@PathVariable Long id, @RequestParam EtatOrdre etat) {
        return ResponseEntity.ok(ordreFabricationService.changerEtat(id, etat));
    }

    @GetMapping("/etat/{etat}")
    @Operation(summary = "Filtrer les ordres par état")
    public ResponseEntity<List<OrdreFabricationDTO>> getByEtat(@PathVariable EtatOrdre etat) {
        return ResponseEntity.ok(ordreFabricationService.getByEtat(etat));
    }

    @GetMapping("/periode")
    @Operation(summary = "Filtrer les ordres par période")
    public ResponseEntity<List<OrdreFabricationDTO>> getByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(ordreFabricationService.getByDateRange(debut, fin));
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher les ordres par nom de projet")
    public ResponseEntity<List<OrdreFabricationDTO>> searchByProjet(@RequestParam String projet) {
        return ResponseEntity.ok(ordreFabricationService.searchByProjet(projet));
    }
}
