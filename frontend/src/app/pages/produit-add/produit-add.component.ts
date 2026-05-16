import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { ProduitService } from '../../services/produit.service';
import { Produit } from '../../models/models';

@Component({
  selector: 'app-produit-add',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './produit-add.component.html'
})
export class ProduitAddComponent implements OnInit {
  produit: any = {
    nom: '',
    type: 'Matière première',
    stock: 0,
    fournisseur: '',
    composantsIds: []
  };

  allProduits: Produit[] = [];
  matieresEtComposants: Produit[] = [];
  selectedComposants: { [id: number]: boolean } = {};
  
  submitting = false;
  error = '';

  constructor(
    private produitService: ProduitService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAllProduits();
  }

  loadAllProduits(): void {
    this.produitService.getAllProduits().subscribe({
      next: (data) => {
        this.allProduits = data;
        this.updateIngredientsList();
      },
      error: (err) => console.error(err)
    });
  }

  onTypeChange(): void {
    this.updateIngredientsList();
  }

  updateIngredientsList(): void {
    // Si c'est un produit fini, il peut être composé de composants ou de matières premières
    // Si c'est un composant, il peut être composé de matières premières
    if (this.produit.type === 'Produit fini') {
      this.matieresEtComposants = this.allProduits.filter(p => p.type !== 'Produit fini');
    } else if (this.produit.type === 'Composant') {
      this.matieresEtComposants = this.allProduits.filter(p => p.type === 'Matière première');
    } else {
      this.matieresEtComposants = [];
    }
    
    // Reset selections
    this.selectedComposants = {};
  }

  onSubmit(): void {
    if (!this.produit.nom || !this.produit.type || !this.produit.fournisseur) {
      this.error = 'Veuillez remplir tous les champs obligatoires.';
      return;
    }

    this.submitting = true;
    this.error = '';

    // Collecter les IDs des composants sélectionnés
    this.produit.composantsIds = Object.keys(this.selectedComposants)
      .map(id => Number(id))
      .filter(id => this.selectedComposants[id]);

    this.produitService.createProduit(this.produit).subscribe({
      next: () => {
        this.router.navigate(['/produits']);
      },
      error: (err) => {
        this.error = 'Erreur lors de la création du produit.';
        this.submitting = false;
        console.error(err);
      }
    });
  }
}
