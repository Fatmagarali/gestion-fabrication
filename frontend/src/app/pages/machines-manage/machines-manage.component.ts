import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MachineService } from '../../services/machine.service';
import { Machine, EtatMachine } from '../../models/models';

@Component({
  selector: 'app-machines-manage',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './machines-manage.component.html',
  styleUrls: ['./machines-manage.component.css']
})
export class MachinesManageComponent implements OnInit {
  machines: Machine[] = [];
  loading = true;
  error = '';
  etats = Object.values(EtatMachine);

  constructor(private machineService: MachineService) {}

  ngOnInit(): void {
    this.loadMachines();
  }

  loadMachines(): void {
    this.loading = true;
    this.error = '';
    this.machineService.getAllMachines().subscribe({
      next: (data) => {
        this.machines = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des machines.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  enregistrerMaintenance(id: number | undefined): void {
    if (!id) return;
    this.machineService.enregistrerMaintenance(id).subscribe({
      next: (updated) => {
        const index = this.machines.findIndex(m => m.id === updated.id);
        if (index !== -1) {
          this.machines[index] = updated;
        }
      },
      error: (err) => {
        console.error('Erreur lors de la maintenance', err);
        alert('Erreur lors de l\'enregistrement de la maintenance.');
      }
    });
  }

  changerEtat(id: number | undefined, nouvelEtat: any): void {
    if (!id) return;
    this.machineService.changerEtat(id, nouvelEtat as EtatMachine).subscribe({
      next: (updated) => {
        const index = this.machines.findIndex(m => m.id === updated.id);
        if (index !== -1) {
          this.machines[index] = updated;
        }
      },
      error: (err) => {
        console.error('Erreur lors du changement d\'état', err);
        alert('Erreur lors du changement d\'état.');
      }
    });
  }

  getBadgeClass(etat: EtatMachine): string {
    switch (etat) {
      case EtatMachine.DISPONIBLE: return 'badge-success';
      case EtatMachine.EN_MARCHE: return 'badge-info';
      case EtatMachine.EN_PANNE: return 'badge-danger';
      case EtatMachine.EN_MAINTENANCE: return 'badge-warning';
      default: return 'badge-info';
    }
  }
}
