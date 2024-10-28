import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

import { VeiwTodoComponent } from './veiw-todo/veiw-todo.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { AuthGuardGuard } from './guard/auth-guard.guard';
import { ErrorcomponentComponent } from './errorcomponent/errorcomponent.component';
import { AddTodoComponent } from './add-todo/add-todo.component';
import { UpdateTodoComponent } from './update-todo/update-todo.component';

const routes: Routes = [
  {path : "register", component : RegisterComponent},
  {path : "login", component : LoginComponent},
  {path: '', redirectTo: '/landing', pathMatch: 'full'},
  {path:'add',component:AddTodoComponent,canActivate: [AuthGuardGuard]},
  {path:'view', component:VeiwTodoComponent,canActivate: [AuthGuardGuard]},

  {path:'landing', component:LandingPageComponent},
   {path:'view/update', component:UpdateTodoComponent,canActivate: [AuthGuardGuard]},
  { path:"**",component:ErrorcomponentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
