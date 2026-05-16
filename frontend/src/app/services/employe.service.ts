import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Employe } from '../models/models';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmployeService {
  private apiUrl = `${environment.apiUrl}/employes`;

  constructor(private http: HttpClient) { }

  getAllEmployes(): Observable<Employe[]> {
    return this.http.get<Employe[]>(this.apiUrl);
  }

  getEmployeById(id: number): Observable<Employe> {
    return this.http.get<Employe>(`${this.apiUrl}/${id}`);
  }

  createEmploye(employe: Employe): Observable<Employe> {
    return this.http.post<Employe>(this.apiUrl, employe);
  }

  updateEmploye(id: number, employe: Employe): Observable<Employe> {
    return this.http.put<Employe>(`${this.apiUrl}/${id}`, employe);
  }

  deleteEmploye(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  assignerMachine(employeId: number, machineId: number): Observable<Employe> {
    return this.http.put<Employe>(`${this.apiUrl}/${employeId}/assigner/${machineId}`, {});
  }

  desassignerMachine(employeId: number): Observable<Employe> {
    return this.http.put<Employe>(`${this.apiUrl}/${employeId}/desassigner`, {});
  }

  getEmployesDisponibles(): Observable<Employe[]> {
    return this.http.get<Employe[]>(`${this.apiUrl}/disponibles`);
  }
}
