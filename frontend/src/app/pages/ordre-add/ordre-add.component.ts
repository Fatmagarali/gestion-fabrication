import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { OrdreFabricationService } from '../../services/ordre-fabrication.service';
import { ProduitService } from '../../services/produit.service';
import { MachineService } from '../../services/machine.service';
import { EmployeService } from '../../services/employe.service';
import { OrdreFabrication, Produit, Machine, Employe, EtatOrdre, EtatMachine } from '../../models/models';

@Component({
  selector: 'app-ordre-add',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ordre-add.component.html',
  styleUrls: ['./ordre-add.component.css']
})
export class OrdreAddComponent implements OnInit {
  ordre: Partial<OrdreFabrication> = {
    projet: '',
    quantite: 1,
    date: new Date().toISOString().split('T')[0],
    etat: EtatOrdre.EN_ATTENTE
  };
  
  produits: Produit[] = [];
  produitsFinis: Produit[] = [];
  composants: Produit[] = [];
  matieresPremieres: Produit[] = [];
  
  machines: Machine[] = [];
  employes: Employe[] = [];

  // Pour stocker les employés cochés
  selectedEmployes: { [id: number]: boolean } = {};

  submitting = false;
  error = '';

  constructor(
    private ordreService: OrdreFabricationService,
    private produitService: ProduitService,
    private machineService: MachineService,
    private employeService: EmployeService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadProduits();
    this.loadMachines();
    this.loadEmployes();
  }

  loadProduits(): void {
    this.produitService.getAllProduits().subscribe({
      next: (data) => {
        this.produits = data;
        this.produitsFinis = data.filter(p => p.type === 'Produit fini');
        this.composants = data.filter(p => p.type === 'Composant');
        this.matieresPremieres = data.filter(p => p.type === 'Matière première');
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des produits.';
        console.error(err);
      }
    });
  }

  loadMachines(): void {
    this.machineService.getAllMachines().subscribe({
      next: (data) => this.machines = data.filter(m => m.etat === EtatMachine.DISPONIBLE || m.etat === EtatMachine.EN_MARCHE),
      error: (err) => console.error(err)
    });
  }

  loadEmployes(): void {
    this.employeService.getAllEmployes().subscribe({
      next: (data) => {
        this.employes = data;
        this.employes.forEach(e => {
          if (e.id) this.selectedEmployes[e.id] = false;
        });
      },
      error: (err) => console.error(err)
    });
  }

  onSubmit(): void {
    if (!this.ordre.projet || !this.ordre.produitId || !this.ordre.quantite || !this.ordre.date) {
      this.error = 'Veuillez remplir tous les champs obligatoires (*).';
      return;
    }

    // Récupérer les ID des employés sélectionnés
    const employesIds = Object.keys(this.selectedEmployes)
      .map(id => Number(id))
      .filter(id => this.selectedEmployes[id]);

    this.ordre.employesIds = employesIds;

    this.submitting = true;
    this.error = '';

    this.ordreService.createOrdre(this.ordre as OrdreFabrication).subscribe({
      next: () => {
        this.router.navigate(['/ordres']);
      },
      error: (err) => {
        this.error = 'Erreur lors de la création de l\'ordre.';
        this.submitting = false;
        console.error(err);
      }
    });
  }
}
