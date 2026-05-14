package com.fabrication.backend.service;

import com.fabrication.backend.dto.OrdreFabricationDTO;
import com.fabrication.backend.entity.EtatOrdre;
import com.fabrication.backend.entity.OrdreFabrication;
import com.fabrication.backend.entity.Produit;
import com.fabrication.backend.exception.ResourceNotFoundException;
import com.fabrication.backend.repository.OrdreFabricationRepository;
import com.fabrication.backend.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrdreFabricationService {

    private final OrdreFabricationRepository ordreFabricationRepository;
    private final ProduitRepository produitRepository;

    /**
     * Récupérer tous les ordres de fabrication.
     */
    @Transactional(readOnly = true)
    public List<OrdreFabricationDTO> getAllOrdres() {
        return ordreFabricationRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer un ordre de fabrication par son ID.
     */
    @Transactional(readOnly = true)
    public OrdreFabricationDTO getOrdreById(Long id) {
        OrdreFabrication ordre = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordre de fabrication", id));
        return toDTO(ordre);
    }

    /**
     * Créer un nouvel ordre de fabrication.
     */
    public OrdreFabricationDTO createOrdre(OrdreFabricationDTO dto) {
        OrdreFabrication ordre = toEntity(dto);
        OrdreFabrication saved = ordreFabricationRepository.save(ordre);
        return toDTO(saved);
    }

    /**
     * Mettre à jour un ordre de fabrication existant.
     */
    public OrdreFabricationDTO updateOrdre(Long id, OrdreFabricationDTO dto) {
        OrdreFabrication ordre = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordre de fabrication", id));

        ordre.setProjet(dto.getProjet());
        ordre.setQuantite(dto.getQuantite());
        ordre.setDate(dto.getDate());
        ordre.setEtat(dto.getEtat());

        if (dto.getProduitId() != null) {
            Produit produit = produitRepository.findById(dto.getProduitId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produit", dto.getProduitId()));
            ordre.setProduit(produit);
        }

        OrdreFabrication updated = ordreFabricationRepository.save(ordre);
        return toDTO(updated);
    }

    /**
     * Supprimer un ordre de fabrication.
     */
    public void deleteOrdre(Long id) {
        if (!ordreFabricationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ordre de fabrication", id);
        }
        ordreFabricationRepository.deleteById(id);
    }

    /**
     * Changer l'état d'un ordre de fabrication (suivi).
     */
    public OrdreFabricationDTO changerEtat(Long id, EtatOrdre nouvelEtat) {
        OrdreFabrication ordre = ordreFabricationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ordre de fabrication", id));

        ordre.setEtat(nouvelEtat);
        OrdreFabrication updated = ordreFabricationRepository.save(ordre);
        return toDTO(updated);
    }

    /**
     * Filtrer les ordres par état.
     */
    @Transactional(readOnly = true)
    public List<OrdreFabricationDTO> getByEtat(EtatOrdre etat) {
        return ordreFabricationRepository.findByEtat(etat).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Filtrer les ordres par période.
     */
    @Transactional(readOnly = true)
    public List<OrdreFabricationDTO> getByDateRange(LocalDate debut, LocalDate fin) {
        return ordreFabricationRepository.findByDateBetween(debut, fin).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Rechercher les ordres par nom de projet.
     */
    @Transactional(readOnly = true)
    public List<OrdreFabricationDTO> searchByProjet(String projet) {
        return ordreFabricationRepository.findByProjetContainingIgnoreCase(projet).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Mappers =====

    private OrdreFabricationDTO toDTO(OrdreFabrication ordre) {
        return OrdreFabricationDTO.builder()
                .id(ordre.getId())
                .projet(ordre.getProjet())
                .produitId(ordre.getProduit().getId())
                .produitNom(ordre.getProduit().getNom())
                .quantite(ordre.getQuantite())
                .date(ordre.getDate())
                .etat(ordre.getEtat())
                .build();
    }

    private OrdreFabrication toEntity(OrdreFabricationDTO dto) {
        Produit produit = produitRepository.findById(dto.getProduitId())
                .orElseThrow(() -> new ResourceNotFoundException("Produit", dto.getProduitId()));

        return OrdreFabrication.builder()
                .projet(dto.getProjet())
                .produit(produit)
                .quantite(dto.getQuantite())
                .date(dto.getDate())
                .etat(dto.getEtat())
                .build();
    }
}
