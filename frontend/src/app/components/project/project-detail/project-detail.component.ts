import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { ProjectService } from '../../../services/project.service';
import { FileService } from '../../../services/file.service';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-project-detail',
  standalone: true,
  templateUrl: './project-detail.component.html',
  imports: [DatePipe, CommonModule, RouterModule]
})
export class ProjectDetailComponent implements OnInit {
  project: any;
  files: any[] = [];
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private projectService: ProjectService,
    private fileService: FileService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    console.log(id);
    this.loadProject(id);
    this.loadFiles(id);
  }

  loadProject(id: number): void {
    this.projectService.getById(id).subscribe(project => {
      this.project = project;
    });
  }

  loadFiles(id: number): void {
    this.fileService.getFilesByProject(id).subscribe(files => {
      this.files = files;
      this.loading = false;
    });
  }

  downloadFile(fileId: number): void {
    this.fileService.downloadFile(fileId).subscribe(blob => {
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = 'archivo';
      a.click();
    });
  }
}
