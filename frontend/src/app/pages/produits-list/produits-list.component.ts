import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProduitService } from '../../services/produit.service';
import { Produit } from '../../models/models';
import { RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-produits-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './produits-list.component.html'
})
export class ProduitsListComponent implements OnInit {
  produits: Produit[] = [];
  filteredProduits: Produit[] = [];
  activeFilter: string = 'Tous';
  types: string[] = ['Tous', 'Matière première', 'Composant', 'Produit fini'];
  loading = true;
  error = '';

  constructor(
    private produitService: ProduitService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.loadProduits();
  }

  loadProduits(): void {
    this.loading = true;
    this.error = '';
    this.produitService.getAllProduits().subscribe({
      next: (data) => {
        this.produits = data;
        this.applyFilter('Tous');
        this.loading = false;
      },
      error: (err) => {
        console.error('Erreur lors du chargement des produits', err);
        this.error = 'Impossible de charger les produits. Le serveur est-il démarré ?';
        this.loading = false;
      }
    });
  }

  applyFilter(type: string): void {
    this.activeFilter = type;
    if (type === 'Tous') {
      this.filteredProduits = [...this.produits];
    } else {
      this.filteredProduits = this.produits.filter(p => p.type === type);
    }
  }

  getStockStatus(stock: number): { text: string, class: string, icon: string } {
    if (stock === 0) return { text: 'Rupture', class: 'badge-danger', icon: '🚨' };
    if (stock <= 50) return { text: 'Peu', class: 'badge-warning', icon: '⚠️' };
    if (stock <= 500) return { text: 'Normal', class: 'badge-info', icon: '✅' };
    return { text: 'Abondant', class: 'badge-success', icon: '🌟' };
  }

  passerOrdreAchat(prod: Produit): void {
    const quantite = prompt(`Combien de "${prod.nom}" voulez-vous commander ?`, "100");
    if (quantite && !isNaN(Number(quantite))) {
      this.http.post(`${environment.apiUrl}/achats?produitId=${prod.id}&quantite=${quantite}`, {}).subscribe({
        next: () => {
          alert(`Ordre d'achat passé avec succès ! Le stock a été mis à jour.`);
          this.loadProduits();
        },
        error: (err) => {
          console.error(err);
          alert('Erreur lors de la création de l\'ordre d\'achat.');
        }
      });
    }
  }
}
