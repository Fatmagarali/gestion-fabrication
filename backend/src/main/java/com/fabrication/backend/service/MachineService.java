package com.fabrication.backend.service;

import com.fabrication.backend.dto.MachineDTO;
import com.fabrication.backend.entity.EtatMachine;
import com.fabrication.backend.entity.Machine;
import com.fabrication.backend.exception.ResourceNotFoundException;
import com.fabrication.backend.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MachineService {

    private final MachineRepository machineRepository;

    /**
     * Récupérer toutes les machines.
     */
    @Transactional(readOnly = true)
    public List<MachineDTO> getAllMachines() {
        return machineRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer une machine par son ID.
     */
    @Transactional(readOnly = true)
    public MachineDTO getMachineById(Long id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine", id));
        return toDTO(machine);
    }

    /**
     * Créer une nouvelle machine.
     */
    public MachineDTO createMachine(MachineDTO dto) {
        Machine machine = toEntity(dto);
        Machine saved = machineRepository.save(machine);
        return toDTO(saved);
    }

    /**
     * Mettre à jour une machine existante.
     */
    public MachineDTO updateMachine(Long id, MachineDTO dto) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine", id));

        machine.setNom(dto.getNom());
        machine.setEtat(dto.getEtat());
        machine.setDerniereMaintenance(dto.getDerniereMaintenance());

        Machine updated = machineRepository.save(machine);
        return toDTO(updated);
    }

    /**
     * Supprimer une machine.
     */
    public void deleteMachine(Long id) {
        if (!machineRepository.existsById(id)) {
            throw new ResourceNotFoundException("Machine", id);
        }
        machineRepository.deleteById(id);
    }

    /**
     * Filtrer les machines par état.
     */
    @Transactional(readOnly = true)
    public List<MachineDTO> getByEtat(EtatMachine etat) {
        return machineRepository.findByEtat(etat).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Enregistrer une maintenance pour une machine.
     * Met l'état en DISPONIBLE et la date de maintenance à aujourd'hui.
     */
    public MachineDTO enregistrerMaintenance(Long id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine", id));

        machine.setDerniereMaintenance(LocalDate.now());

        Machine updated = machineRepository.save(machine);
        return toDTO(updated);
    }

    public MachineDTO changerEtat(Long id, EtatMachine nouvelEtat) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Machine", id));
        machine.setEtat(nouvelEtat);
        return toDTO(machineRepository.save(machine));
    }

    // ===== Mappers =====

    private MachineDTO toDTO(Machine machine) {
        return MachineDTO.builder()
                .id(machine.getId())
                .nom(machine.getNom())
                .etat(machine.getEtat())
                .derniereMaintenance(machine.getDerniereMaintenance())
                .build();
    }

    private Machine toEntity(MachineDTO dto) {
        return Machine.builder()
                .nom(dto.getNom())
                .etat(dto.getEtat())
                .derniereMaintenance(dto.getDerniereMaintenance())
                .build();
    }
}
