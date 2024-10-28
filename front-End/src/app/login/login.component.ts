import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RouterService } from '../services/routerService/router.service';
import { AuthService } from '../services/userService/auth.service';
import { UserService } from '../services/userService/user.service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {
  public loginForm!:FormGroup;
  constructor(private formBuilder:FormBuilder, private router:RouterService,private auth:AuthService,private userService:UserService,private snackBar:MatSnackBar){
  }
  ngOnInit(): void {
    this.setFormFields();
  }
  public setFormFields() : void{
    this.loginForm = this.formBuilder.group({
      userEmail : ["",[Validators.required,Validators.email]],
      password : ["",[Validators.required,Validators.minLength(8)]],})
    
  }
  getUserDetails(data: string) {
    this.userService.getUserInfo(data).subscribe(
      (res) => {
        this.auth.setUserDetails(res);
      },
      (error: HttpErrorResponse) => {
        console.error("Error:", error);
      }
     );
  }
  onSubmit(){
    // alert(this.loginForm.value);
      
      this.userService.loginUser(this.loginForm.value).subscribe((data)=>{
        this.auth.setToken(data);
       
        this.snackBar.open('Login Successfull!', 'Close', {
          duration: 5000, 
          verticalPosition: 'bottom', 
          horizontalPosition: 'center',
          panelClass: ['success-snackbar']
       });
       this.getUserDetails(data);
       this.router.navigateToView(); 
      },
    (error: HttpErrorResponse) => {
      console.error('Error logging user:', error);
      let errorMessage = error.error;
      if (error.error && error.error.message) {
        errorMessage = error.error.message;
      }
      this.snackBar.open(errorMessage, 'Close', {
        duration: 5000,
        verticalPosition: 'bottom',
        horizontalPosition: 'center',
        panelClass: ['error-snackbar']
      });
    })

  }
  

}
