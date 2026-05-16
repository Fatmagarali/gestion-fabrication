import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployeService } from '../../services/employe.service';
import { MachineService } from '../../services/machine.service';
import { Employe, Machine } from '../../models/models';

@Component({
  selector: 'app-employes-manage',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employes-manage.component.html',
  styleUrls: ['./employes-manage.component.css']
})
export class EmployesManageComponent implements OnInit {
  employes: Employe[] = [];
  machines: Machine[] = [];
  loading = true;
  error = '';

  constructor(
    private employeService: EmployeService,
    private machineService: MachineService
  ) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.loading = true;
    this.error = '';
    
    // Charger employés et machines
    this.employeService.getAllEmployes().subscribe({
      next: (emps) => {
        this.employes = emps;
        this.machineService.getAllMachines().subscribe({
          next: (machs) => {
            this.machines = machs;
            this.loading = false;
          },
          error: (err) => {
            this.error = 'Erreur lors du chargement des machines.';
            this.loading = false;
          }
        });
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des employés.';
        this.loading = false;
      }
    });
  }

  assigner(employeId: number | undefined, machineId: string): void {
    if (!employeId || !machineId) return;
    this.employeService.assignerMachine(employeId, parseInt(machineId)).subscribe({
      next: (updated) => {
        const index = this.employes.findIndex(e => e.id === updated.id);
        if (index !== -1) {
          this.employes[index] = updated;
        }
      },
      error: (err) => alert('Erreur lors de l\'assignation.')
    });
  }

  desassigner(employeId: number | undefined): void {
    if (!employeId) return;
    this.employeService.desassignerMachine(employeId).subscribe({
      next: (updated) => {
        const index = this.employes.findIndex(e => e.id === updated.id);
        if (index !== -1) {
          this.employes[index] = updated;
        }
      },
      error: (err) => alert('Erreur lors de la désassignation.')
    });
  }
}
