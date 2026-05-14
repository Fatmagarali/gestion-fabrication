package com.fabrication.backend.repository;

import com.fabrication.backend.entity.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, Long> {

    List<Employe> findByPoste(String poste);

    List<Employe> findByMachineAssigneeId(Long machineId);

    List<Employe> findByMachineAssigneeIsNull();

    List<Employe> findByNomContainingIgnoreCase(String nom);
}
