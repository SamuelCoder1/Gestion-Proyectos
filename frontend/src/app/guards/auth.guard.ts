import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    console.log('Token en AuthGuard:', token); 

    if (token) {
      return true;
    }

    this.router.navigate(['/login']);
    return false;
  }
}
