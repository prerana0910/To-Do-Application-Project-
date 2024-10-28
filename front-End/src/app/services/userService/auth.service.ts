import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

//   constructor() { }
//   public token!:any;
//   public isLoggedIn : boolean = false;
//   public setToken(userToken:any){
//     this.token = userToken;
//     console.log(this.token);
//   }
//   public setLogInStatus(status:boolean){
//     this.isLoggedIn=status;

//   }
  
constructor(private snackbar:MatSnackBar){}
public token!:any;
  public userName!: string;
  public emailId!: string;
  public password!: string;
  public phoneNumber!: string;
  public isLoggedIn : boolean = false;
  public setUserDetails(user : User) : void{
    this.isLoggedIn = true;
    this.userName = user.userName;
    this.emailId = user.userEmail;
    this.password = user.password;
    this.phoneNumber = user.phoneNo;
  }
  
  public setToken(userToken:any){
    this.token = userToken;
    console.log(this.token);
  }

  public logOutUser(){
    this.isLoggedIn = false;
    this.userName = '';
    this.emailId = '';
    this.password = '';
    this.phoneNumber = ''
    this.token = '';
    this.snackbar.open('Logout Successfull!', 'Close', {
      duration: 5000, 
      verticalPosition: 'bottom', 
      horizontalPosition: 'center',
      panelClass: ['success-snackbar']
    });
    
  }
  // loginStatus(){
  //   if(sessionStorage.getItem('Token')){
  //     this.isLoggedIn=true
  //   }
  //   else{
  //     this.isLoggedIn=false
  //   }
  //   return this.isLoggedIn
  // }
}

