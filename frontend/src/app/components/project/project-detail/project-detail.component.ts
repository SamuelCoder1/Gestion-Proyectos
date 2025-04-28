import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { ProjectService } from '../../../services/project.service';
import { FileService } from '../../../services/file.service';
import { FileResponse } from '../../../models/file';
import { environment } from '../../../../environments/environment';

interface GroupedFiles {
  images: FileResponse[];
  videos: FileResponse[];
  audios: FileResponse[];
  documents: FileResponse[];
  others: FileResponse[];
}

@Component({
  selector: 'app-project-detail',
  standalone: true,
  imports: [DatePipe, CommonModule, RouterModule],
  templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent implements OnInit {
  project: any;
  files: FileResponse[] = [];
  grouped: GroupedFiles = {
    images: [], videos: [], audios: [], documents: [], others: []
  };
  loading = true;
  projectId!: number;
  selectedFiles: File[] = [];

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    this.projectId = Number(this.route.snapshot.paramMap.get('id'));
    this.loadProject(this.projectId);
    this.loadFiles(this.projectId);
  }

  loadProject(id: number): void {
    this.projectService.getById(id)
      .subscribe(p => this.project = p);
  }

  loadFiles(id: number): void {
    this.fileService.getFilesByProject(id)
      .subscribe(fs => {
        this.files = fs;
        this.categorizeFiles(fs);
        this.loading = false;
      }, () => {
        this.files = [];
        this.loading = false;
      });
  }

  private categorizeFiles(fs: FileResponse[]) {
    this.grouped = { images: [], videos: [], audios: [], documents: [], others: [] };
    fs.forEach(f => {
      if (f.type.startsWith('image/')) {
        this.grouped.images.push(f);
      } else if (f.type.startsWith('video/')) {
        this.grouped.videos.push(f);
      } else if (f.type.startsWith('audio/')) {
        this.grouped.audios.push(f);
      } else if (f.type === 'application/pdf' || f.type.startsWith('text/')) {
        this.grouped.documents.push(f);
      } else {
        this.grouped.others.push(f);
      }
    });
  }

  fileDownloadUrl(f: FileResponse): string {
    return `${environment.apiUrl}${f.url}`;
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFiles = input.files ? Array.from(input.files) : [];
  }

  upload(): void {
    if (!this.selectedFiles.length) return;
    const token = localStorage.getItem('token') || '';
    this.fileService.uploadFiles(this.projectId, this.selectedFiles, token)
      .subscribe(() => {
        this.selectedFiles = [];
        this.loadFiles(this.projectId);
      });
  }

  downloadFile(fileId: number): void {
    this.fileService.downloadFile(fileId)
      .subscribe(blob => {
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        const f = this.files.find(x => x.id === fileId);
        a.href = url;
        a.download = f?.name ?? 'archivo';
        a.click();
        URL.revokeObjectURL(url);
      });
  }

  delete(fileId: number): void {
    if (!confirm('¿Eliminar este archivo?')) return;
    this.fileService.deleteFile(fileId)
      .subscribe(() => this.loadFiles(this.projectId));
  }
}
