import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { ProjectService } from '../../../services/project.service';
import { ProjectRequest } from '../../../models/project';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-project-create',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './project-create.component.html',
  styleUrls: ['./project-create.component.css']
})
export class ProjectCreateComponent {
  project: ProjectRequest = {
    title: '',
    description: '',
    deliveryDate: '',
    status: 'Pendiente'
  };

  constructor(private projectService: ProjectService, public dialogRef: MatDialogRef<ProjectCreateComponent>) {}

  createProject(): void {
    this.projectService.create(this.project).subscribe(
      () => {
        this.dialogRef.close(true); // Cerrar el modal y confirmar
      },
      (error) => {
        console.error('Error creando proyecto', error);
      }
    );
  }

  closeDialog(): void {
    this.dialogRef.close();
  }
}
