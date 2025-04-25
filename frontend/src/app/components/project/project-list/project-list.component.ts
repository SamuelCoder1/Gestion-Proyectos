import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatDialogModule, MatDialog } from '@angular/material/dialog';
import { ProjectService } from '../../../services/project.service';
import { Project } from '../../../models/project';
import { ProjectCreateComponent } from '../project-create/project-create.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-project-list',
  standalone: true,
  imports: [CommonModule, FormsModule, MatDialogModule, RouterModule],
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {
  projects: Project[] = [];
  loading = false;

  constructor(private projectService: ProjectService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.fetchProjects();
  }

  fetchProjects(): void {
    this.loading = true;
    this.projectService.getAll().subscribe(
      (data) => {
        this.projects = data;
        this.loading = false;
      },
      (error) => {
        console.error('Error fetching projects', error);
        this.loading = false;
      }
    );
  }

  openCreateDialog(): void {
    const dialogRef = this.dialog.open(ProjectCreateComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.fetchProjects();
      }
    });
  }

  deleteProject(id: number): void {
    if (confirm('¿Seguro que deseas eliminar este proyecto?')) {
      this.projectService.delete(id).subscribe(() => {
        this.fetchProjects();
      });
    }
  }
}
