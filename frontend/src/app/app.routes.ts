import { Routes } from '@angular/router';
import { OrdresListComponent } from './pages/ordres-list/ordres-list.component';
import { OrdreAddComponent } from './pages/ordre-add/ordre-add.component';
import { MachinesManageComponent } from './pages/machines-manage/machines-manage.component';
import { EmployesManageComponent } from './pages/employes-manage/employes-manage.component';
import { ProduitsListComponent } from './pages/produits-list/produits-list.component';
import { ProduitAddComponent } from './pages/produit-add/produit-add.component';

export const routes: Routes = [
  { path: '', redirectTo: 'ordres', pathMatch: 'full' },
  { path: 'ordres', component: OrdresListComponent },
  { path: 'ordres/nouveau', component: OrdreAddComponent },
  { path: 'machines', component: MachinesManageComponent },
  { path: 'employes', component: EmployesManageComponent },
  { path: 'produits', component: ProduitsListComponent },
  { path: 'produits/nouveau', component: ProduitAddComponent },
  { path: '**', redirectTo: 'ordres' }
];
