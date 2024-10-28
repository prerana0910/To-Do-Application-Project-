import { Component, inject } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { UserService } from '../services/userService/user.service';
import { Router } from 'express';
import { AuthService } from '../services/userService/auth.service';
import { RouterService } from '../services/routerService/router.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  constructor(
    public userService: UserService,
    public route: RouterService,
    public authService: AuthService
  ) {}
 
    // onLoggingOut() {
    //   let confirmvalue = confirm('Are you sure , Do you want to Logout ?');
    //   if (confirmvalue) {
    //     sessionStorage.removeItem('Token');
    //     this.authService.loginStatus();
    //     this.route.navigateToLoginPage();
    //   }
    // }
    onLoggingOut() {
      let confirmvalue = confirm('Are you sure , Do you want to Logout ?');
      if (confirmvalue) {
      this.authService.logOutUser();
      this.route.navigateToLoginPage();
      }
    }
}
