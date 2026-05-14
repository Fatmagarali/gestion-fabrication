package com.fabrication.backend.controller;

import com.fabrication.backend.dto.ProduitDTO;
import com.fabrication.backend.service.ProduitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "Gestion des produits")
public class ProduitController {

    private final ProduitService produitService;

    @GetMapping
    @Operation(summary = "Récupérer tous les produits")
    public ResponseEntity<List<ProduitDTO>> getAllProduits() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit par ID")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable Long id) {
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau produit")
    public ResponseEntity<ProduitDTO> createProduit(@Valid @RequestBody ProduitDTO dto) {
        ProduitDTO created = produitService.createProduit(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un produit")
    public ResponseEntity<ProduitDTO> updateProduit(@PathVariable Long id, @Valid @RequestBody ProduitDTO dto) {
        return ResponseEntity.ok(produitService.updateProduit(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Rechercher des produits par nom")
    public ResponseEntity<List<ProduitDTO>> searchByNom(@RequestParam String nom) {
        return ResponseEntity.ok(produitService.searchByNom(nom));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Filtrer les produits par type")
    public ResponseEntity<List<ProduitDTO>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(produitService.getByType(type));
    }
}
