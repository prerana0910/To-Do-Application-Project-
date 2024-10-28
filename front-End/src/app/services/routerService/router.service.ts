import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class RouterService {
  navigateByUrl(arg0: string) {
    throw new Error('Method not implemented.');
  }

  constructor(private router : Router) { }

  navigateToLoginPage(){
    this.router.navigate(["login"]);
  }
  navigateToRegister(){
    this.router.navigate(["register"]);
  }
  
  navigateToView(){
    this.router.navigate(["view"]);
  }
  navigateToUpdate(){
    this.router.navigate(["view/update"])
  }
  
 
}
