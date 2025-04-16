import { Injectable } from '@angular/core';
import { Project, ProjectRequest } from '../models/project';
import { Observable } from 'rxjs';
import { ApiService } from './api';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProjectService {

  private baseUrl = '/projects';

  constructor(private api: ApiService, private http: HttpClient) {}

  getAll(): Observable<Project[]> {
    const token = localStorage.getItem('token');

    if (!token) {
      throw new Error('Token de autenticación no disponible');
    }

    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });

    return this.http.get<Project[]>(`http://localhost:8080/projects/getAll`, {
      withCredentials: true
    });
  }

  getById(id: number): Observable<Project> {
    return this.api.get<Project>(`${this.baseUrl}/getById/${id}`);
  }

  create(data: ProjectRequest): Observable<Project> {
    return this.api.post<Project>(`${this.baseUrl}/create`, data);
  }

  update(id: number, data: ProjectRequest): Observable<Project> {
    return this.api.put<Project>(`${this.baseUrl}/update/${id}`, data);
  }

  delete(id: number): Observable<void> {
    return this.api.delete<void>(`${this.baseUrl}/delete/${id}`);
  }
}
