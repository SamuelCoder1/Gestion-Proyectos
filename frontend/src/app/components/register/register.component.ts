import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { RegisterRequest } from '../../models/register-request.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  name: string = '';
  email: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    console.log('✅ Componente de Registro cargado correctamente');
  }

  register(): void {
    const newUser: RegisterRequest = {
      name: this.name,
      email: this.email,
      password: this.password,
    };

    this.authService.register(newUser).subscribe({
      next: (response) => {
        alert('✅ ¡Registro exitoso! Ahora puedes iniciar sesión.');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('❌ Error en el registro:', error);
        this.errorMessage = 'Registro fallido. Intenta nuevamente.';
      }
    });
  }
}
