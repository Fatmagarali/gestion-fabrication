package com.fabrication.backend.repository;

import com.fabrication.backend.entity.EtatMachine;
import com.fabrication.backend.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

    List<Machine> findByEtat(EtatMachine etat);

    List<Machine> findByNomContainingIgnoreCase(String nom);
}
