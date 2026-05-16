package com.fabrication.backend.controller;

import com.fabrication.backend.entity.OrdreAchat;
import com.fabrication.backend.entity.Produit;
import com.fabrication.backend.repository.OrdreAchatRepository;
import com.fabrication.backend.repository.ProduitRepository;
import com.fabrication.backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/achats")
@RequiredArgsConstructor
public class OrdreAchatController {

    private final OrdreAchatRepository ordreAchatRepository;
    private final ProduitRepository produitRepository;

    @GetMapping
    public List<OrdreAchat> getAll() {
        return ordreAchatRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<OrdreAchat> create(@RequestParam Long produitId, @RequestParam Integer quantite) {
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", produitId));
        
        OrdreAchat ordre = OrdreAchat.builder()
                .produit(produit)
                .quantite(quantite)
                .dateAchat(LocalDate.now())
                .fournisseur(produit.getFournisseur())
                .statut("RECU") // On le marque directement RECU pour simplifier et augmenter le stock immédiatement
                .build();
        
        produit.setStock(produit.getStock() + quantite);
        produitRepository.save(produit);
        
        return ResponseEntity.ok(ordreAchatRepository.save(ordre));
    }
}
