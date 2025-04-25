import { Component, Input, OnInit } from '@angular/core';
import { FileService } from '../../../services/file.service';
import { DomSanitizer } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-file-manager',
  templateUrl: './file-manager.component.html',
  imports : [RouterModule]
})
export class FileManagerComponent implements OnInit {
  @Input() projectId!: number;
  selectedFiles: File[] = [];
  files: any[] = [];

  constructor(private fileService: FileService, private sanitizer: DomSanitizer) {}

  ngOnInit() {
    this.loadFiles();
  }

  onFileSelected(event: any) {
    this.selectedFiles = Array.from(event.target.files);
  }

  uploadFiles() {
    const token = localStorage.getItem('token') || '';
    this.fileService.uploadFiles(this.projectId, this.selectedFiles, token).subscribe(() => {
      this.selectedFiles = [];
      this.loadFiles();
    });
  }

  loadFiles() {
    this.fileService.getFilesByProject(this.projectId).subscribe((data) => {
      this.files = data;
    });
  }

  downloadFile(fileId: number) {
    this.fileService.downloadFile(fileId).subscribe((blob) => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'file';
      a.click();
    });
  }

  deleteFile(fileId: number) {
    this.fileService.deleteFile(fileId).subscribe(() => {
      this.loadFiles();
    });
  }
}
