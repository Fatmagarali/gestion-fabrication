import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OrdreFabrication, EtatOrdre } from '../models/models';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrdreFabricationService {
  private apiUrl = `${environment.apiUrl}/ordres`;

  constructor(private http: HttpClient) { }

  getAllOrdres(): Observable<OrdreFabrication[]> {
    return this.http.get<OrdreFabrication[]>(this.apiUrl);
  }

  getOrdreById(id: number): Observable<OrdreFabrication> {
    return this.http.get<OrdreFabrication>(`${this.apiUrl}/${id}`);
  }

  createOrdre(ordre: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.post<OrdreFabrication>(this.apiUrl, ordre);
  }

  updateOrdre(id: number, ordre: OrdreFabrication): Observable<OrdreFabrication> {
    return this.http.put<OrdreFabrication>(`${this.apiUrl}/${id}`, ordre);
  }

  deleteOrdre(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  changerEtat(id: number, etat: EtatOrdre): Observable<OrdreFabrication> {
    return this.http.patch<OrdreFabrication>(`${this.apiUrl}/${id}/etat?etat=${etat}`, {});
  }

  getByEtat(etat: EtatOrdre): Observable<OrdreFabrication[]> {
    return this.http.get<OrdreFabrication[]>(`${this.apiUrl}/etat/${etat}`);
  }
}
