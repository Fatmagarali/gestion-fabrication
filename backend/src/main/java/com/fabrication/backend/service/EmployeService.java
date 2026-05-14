package com.fabrication.backend.service;

import com.fabrication.backend.dto.EmployeDTO;
import com.fabrication.backend.entity.Employe;
import com.fabrication.backend.entity.Machine;
import com.fabrication.backend.exception.ResourceNotFoundException;
import com.fabrication.backend.repository.EmployeRepository;
import com.fabrication.backend.repository.MachineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final MachineRepository machineRepository;

    /**
     * Récupérer tous les employés.
     */
    @Transactional(readOnly = true)
    public List<EmployeDTO> getAllEmployes() {
        return employeRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer un employé par son ID.
     */
    @Transactional(readOnly = true)
    public EmployeDTO getEmployeById(Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", id));
        return toDTO(employe);
    }

    /**
     * Créer un nouvel employé.
     */
    public EmployeDTO createEmploye(EmployeDTO dto) {
        Employe employe = toEntity(dto);
        Employe saved = employeRepository.save(employe);
        return toDTO(saved);
    }

    /**
     * Mettre à jour un employé existant.
     */
    public EmployeDTO updateEmploye(Long id, EmployeDTO dto) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", id));

        employe.setNom(dto.getNom());
        employe.setPoste(dto.getPoste());

        if (dto.getMachineAssigneeId() != null) {
            Machine machine = machineRepository.findById(dto.getMachineAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Machine", dto.getMachineAssigneeId()));
            employe.setMachineAssignee(machine);
        } else {
            employe.setMachineAssignee(null);
        }

        Employe updated = employeRepository.save(employe);
        return toDTO(updated);
    }

    /**
     * Supprimer un employé.
     */
    public void deleteEmploye(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employé", id);
        }
        employeRepository.deleteById(id);
    }

    /**
     * Affecter un employé à une machine.
     */
    public EmployeDTO assignerMachine(Long employeId, Long machineId) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", employeId));
        Machine machine = machineRepository.findById(machineId)
                .orElseThrow(() -> new ResourceNotFoundException("Machine", machineId));

        employe.setMachineAssignee(machine);
        Employe updated = employeRepository.save(employe);
        return toDTO(updated);
    }

    /**
     * Désaffecter un employé de sa machine.
     */
    public EmployeDTO desassignerMachine(Long employeId) {
        Employe employe = employeRepository.findById(employeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employé", employeId));

        employe.setMachineAssignee(null);
        Employe updated = employeRepository.save(employe);
        return toDTO(updated);
    }

    /**
     * Récupérer les employés non affectés à une machine.
     */
    @Transactional(readOnly = true)
    public List<EmployeDTO> getEmployesDisponibles() {
        return employeRepository.findByMachineAssigneeIsNull().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupérer les employés affectés à une machine donnée.
     */
    @Transactional(readOnly = true)
    public List<EmployeDTO> getEmployesByMachine(Long machineId) {
        return employeRepository.findByMachineAssigneeId(machineId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ===== Mappers =====

    private EmployeDTO toDTO(Employe employe) {
        EmployeDTO dto = EmployeDTO.builder()
                .id(employe.getId())
                .nom(employe.getNom())
                .poste(employe.getPoste())
                .build();

        if (employe.getMachineAssignee() != null) {
            dto.setMachineAssigneeId(employe.getMachineAssignee().getId());
            dto.setMachineAssigneeNom(employe.getMachineAssignee().getNom());
        }

        return dto;
    }

    private Employe toEntity(EmployeDTO dto) {
        Employe employe = Employe.builder()
                .nom(dto.getNom())
                .poste(dto.getPoste())
                .build();

        if (dto.getMachineAssigneeId() != null) {
            Machine machine = machineRepository.findById(dto.getMachineAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Machine", dto.getMachineAssigneeId()));
            employe.setMachineAssignee(machine);
        }

        return employe;
    }
}
