import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Machine, EtatMachine } from '../models/models';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MachineService {
  private apiUrl = `${environment.apiUrl}/machines`;

  constructor(private http: HttpClient) { }

  getAllMachines(): Observable<Machine[]> {
    return this.http.get<Machine[]>(this.apiUrl);
  }

  getMachineById(id: number): Observable<Machine> {
    return this.http.get<Machine>(`${this.apiUrl}/${id}`);
  }

  createMachine(machine: Machine): Observable<Machine> {
    return this.http.post<Machine>(this.apiUrl, machine);
  }

  updateMachine(id: number, machine: Machine): Observable<Machine> {
    return this.http.put<Machine>(`${this.apiUrl}/${id}`, machine);
  }

  deleteMachine(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  enregistrerMaintenance(id: number): Observable<Machine> {
    return this.http.put<Machine>(`${this.apiUrl}/${id}/maintenance`, {});
  }
  
  getByEtat(etat: EtatMachine): Observable<Machine[]> {
    return this.http.get<Machine[]>(`${this.apiUrl}/etat/${etat}`);
  }

  changerEtat(id: number, etat: EtatMachine): Observable<Machine> {
    return this.http.patch<Machine>(`${this.apiUrl}/${id}/etat?etat=${etat}`, {});
  }
}
