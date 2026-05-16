import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OrdreFabricationService } from '../../services/ordre-fabrication.service';
import { OrdreFabrication, EtatOrdre } from '../../models/models';

@Component({
  selector: 'app-ordres-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ordres-list.component.html',
  styleUrls: ['./ordres-list.component.css']
})
export class OrdresListComponent implements OnInit {
  ordres: OrdreFabrication[] = [];
  loading = true;
  error = '';
  
  // Available states for filtering
  etats = Object.values(EtatOrdre);

  constructor(private ordreService: OrdreFabricationService) {}

  ngOnInit(): void {
    this.loadOrdres();
  }

  loadOrdres(): void {
    this.loading = true;
    this.error = '';
    this.ordreService.getAllOrdres().subscribe({
      next: (data) => {
        this.ordres = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des ordres de fabrication.';
        this.loading = false;
        console.error(err);
      }
    });
  }

  changerEtat(id: number | undefined, nouvelEtat: EtatOrdre | string): void {
    if (!id) return;
    this.ordreService.changerEtat(id, nouvelEtat as EtatOrdre).subscribe({
      next: (updated) => {
        const index = this.ordres.findIndex(o => o.id === updated.id);
        if (index !== -1) {
          this.ordres[index] = updated;
        }
      },
      error: (err) => {
        console.error('Erreur lors du changement d\'état', err);
        alert('Erreur lors du changement d\'état.');
      }
    });
  }

  getBadgeClass(etat: EtatOrdre): string {
    switch (etat) {
      case EtatOrdre.EN_ATTENTE: return 'badge-warning';
      case EtatOrdre.EN_COURS: return 'badge-info';
      case EtatOrdre.TERMINE: return 'badge-success';
      case EtatOrdre.ANNULE: return 'badge-danger';
      default: return 'badge-info';
    }
  }
}
