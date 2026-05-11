import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserContractService {
  private readonly baseUrl = `${environment.apiBaseUrl ?? '/api'}/user`;

  constructor(private http: HttpClient) {}

  getUserById(userId: number) {
    return this.http.get<any>(`${this.baseUrl}/getUser/${userId}`);
  }
}