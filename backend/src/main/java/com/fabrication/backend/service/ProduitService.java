package com.fabrication.backend.service;

import com.fabrication.backend.dto.ProduitDTO;
import com.fabrication.backend.entity.Produit;
import com.fabrication.backend.exception.ResourceNotFoundException;
import com.fabrication.backend.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProduitService {

    private final ProduitRepository produitRepository;

    /**
     * Récupérer tous les produits.
     */
    @Transactional(readOnly = true)
    public List<ProduitDTO> getAllProduits() {
        return produitRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer un produit par son ID.
     */
    @Transactional(readOnly = true)
    public ProduitDTO getProduitById(Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", id));
        return toDTO(produit);
    }

    /**
     * Créer un nouveau produit.
     */
    public ProduitDTO createProduit(ProduitDTO dto) {
        Produit produit = toEntity(dto);
        Produit saved = produitRepository.save(produit);
        return toDTO(saved);
    }

    /**
     * Mettre à jour un produit existant.
     */
    public ProduitDTO updateProduit(Long id, ProduitDTO dto) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit", id));

        produit.setNom(dto.getNom());
        produit.setType(dto.getType());
        produit.setStock(dto.getStock());
        produit.setFournisseur(dto.getFournisseur());

        Produit updated = produitRepository.save(produit);
        return toDTO(updated);
    }

    /**
     * Supprimer un produit.
     */
    public void deleteProduit(Long id) {
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit", id);
        }
        produitRepository.deleteById(id);
    }

    /**
     * Rechercher des produits par nom.
     */
    @Transactional(readOnly = true)
    public List<ProduitDTO> searchByNom(String nom) {
        return produitRepository.findByNomContainingIgnoreCase(nom).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filtrer les produits par type.
     */
    @Transactional(readOnly = true)
    public List<ProduitDTO> getByType(String type) {
        return produitRepository.findByType(type).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Mappers =====

    private ProduitDTO toDTO(Produit produit) {
        return ProduitDTO.builder()
                .id(produit.getId())
                .nom(produit.getNom())
                .type(produit.getType())
                .stock(produit.getStock())
                .fournisseur(produit.getFournisseur())
                .build();
    }

    private Produit toEntity(ProduitDTO dto) {
        return Produit.builder()
                .nom(dto.getNom())
                .type(dto.getType())
                .stock(dto.getStock())
                .fournisseur(dto.getFournisseur())
                .build();
    }
}
