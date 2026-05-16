export enum EtatOrdre {
  EN_ATTENTE = 'EN_ATTENTE',
  EN_COURS = 'EN_COURS',
  TERMINE = 'TERMINE',
  ANNULE = 'ANNULE'
}

export enum EtatMachine {
  DISPONIBLE = 'DISPONIBLE',
  EN_MARCHE = 'EN_MARCHE',
  EN_PANNE = 'EN_PANNE',
  EN_MAINTENANCE = 'EN_MAINTENANCE'
}

export interface Produit {
  id?: number;
  nom: string;
  type: string;
  stock: number;
  fournisseur: string;
}

export interface Machine {
  id?: number;
  nom: string;
  etat: EtatMachine;
  derniereMaintenance?: string; // ISO date string
}

export interface Employe {
  id?: number;
  nom: string;
  poste: string;
  machineAssigneeId?: number | null;
  machineAssigneeNom?: string;
}

export interface OrdreFabrication {
  id?: number;
  projet: string;
  produitId: number;
  produitNom?: string;
  quantite: number;
  date: string; // ISO date string
  etat: EtatOrdre;
  machineId?: number;
  machineNom?: string;
  machineEtat?: string;
  employesIds?: number[];
}
