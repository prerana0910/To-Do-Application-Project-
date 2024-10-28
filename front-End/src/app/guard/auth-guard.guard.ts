import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/userService/auth.service';


@Injectable({
  providedIn: 'root'
})
export class AuthGuardGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate() {
    if (this.authService.token) {
      return true;
    } else {
      this.router.navigateByUrl('/login');
      return false;
    }
  }
}