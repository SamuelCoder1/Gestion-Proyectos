import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FileService {
  private baseUrl = 'http://localhost:8080/files';

  constructor(private http: HttpClient) {}

  uploadFiles(projectId: number, files: File[], token: string): Observable<any> {
    const formData = new FormData();

    files.forEach(file => {
      formData.append('files', file);
    });

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.post(`${this.baseUrl}/upload/${projectId}`, formData, { headers });
  }

  getFilesByProject(projectId: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/project/${projectId}`);
  }

  downloadFile(fileId: number): Observable<Blob> {
    return this.http.get(`${this.baseUrl}/download/${fileId}`, {
      responseType: 'blob'
    });
  }

  deleteFile(fileId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${fileId}`);
  }
}
