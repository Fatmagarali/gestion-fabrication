package com.fabrication.backend.repository;

import com.fabrication.backend.entity.EtatOrdre;
import com.fabrication.backend.entity.OrdreFabrication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdreFabricationRepository extends JpaRepository<OrdreFabrication, Long> {

    List<OrdreFabrication> findByEtat(EtatOrdre etat);

    List<OrdreFabrication> findByProduitId(Long produitId);

    List<OrdreFabrication> findByDateBetween(LocalDate debut, LocalDate fin);

    List<OrdreFabrication> findByProjetContainingIgnoreCase(String projet);
}
