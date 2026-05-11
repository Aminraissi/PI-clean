import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Institution } from '../../loans/models/institution';
import { environment } from 'src/environments/environment';
@Injectable({
  providedIn: 'root'
})
export class InstitutionService {

  constructor(private http: HttpClient) { }
   private apiUrl = `${environment.apiBaseUrl ?? '/api'}/user`;

    getInstitutions() {
    return this.http.get<Institution[]>(`${this.apiUrl}/institutions`);
  }
  
}