import { Component,OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RouterService } from '../services/routerService/router.service';
import { UserService } from '../services/userService/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  public userRegistrationForm!:FormGroup;
  constructor(private formBuilder:FormBuilder,private router:RouterService,private userService:UserService){
  }
  ngOnInit(): void {
    this.setFormFields();
  }
  
  public setFormFields() : void{
    this.userRegistrationForm = this.formBuilder.group({
      userEmail: ["", [Validators.required, Validators.pattern("^[a-zA-Z0-9._%+-]{3,}@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")]],
      userName : ["", [Validators.required]],
      password : ["",[Validators.required,Validators.minLength(8)]],
      phoneNo : ["", [Validators.required,Validators.pattern("^\\d{10}$"), Validators.minLength(10)]], })
    
  }
  // Validators.pattern(/^(?=.[a-z])(?=.[A-Z])(?=.\d)(?=.[$@$!%?&])[A-Za-z\d$@$!%?&]{8,}$/)
  onSubmit(){
    // alert(this.userRegistrationForm.value);
    this.userService.registerUser(this.userRegistrationForm.value).subscribe((data=>{
      console.log(data);
      this.router.navigateToLoginPage();
    }))
   

  }


}
