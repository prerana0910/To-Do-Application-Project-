import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { HeaderComponent } from './header/header.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { RegisterComponent } from './register/register.component';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatMenuModule } from '@angular/material/menu';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatChipsModule } from '@angular/material/chips';
import {MatRadioModule} from '@angular/material/radio';
import { ReactiveFormsModule } from '@angular/forms';
import { LoginComponent } from './login/login.component';
import {MatDatepickerModule} from '@angular/material/datepicker'; 
import {provideNativeDateAdapter} from '@angular/material/core';
import { FooterComponent } from './footer/footer.component';
import { VeiwTodoComponent } from './veiw-todo/veiw-todo.component';
import { HttpClientModule } from '@angular/common/http';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { ErrorcomponentComponent } from './errorcomponent/errorcomponent.component';
import { UpdateTodoComponent } from './update-todo/update-todo.component';
import { AddTodoComponent } from './add-todo/add-todo.component';
import { DatePipe } from '@angular/common';
import {MatCheckboxModule} from '@angular/material/checkbox';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    RegisterComponent,
    LoginComponent,
   
    FooterComponent,
    VeiwTodoComponent,
    LandingPageComponent,
    ErrorcomponentComponent,
    UpdateTodoComponent,
    AddTodoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    FormsModule,
    MatGridListModule,
    MatCardModule,
    MatMenuModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    MatSelectModule,
    MatChipsModule,
    MatRadioModule,
    MatDatepickerModule,
    ReactiveFormsModule,
    MatCheckboxModule
    
    
  ],
  providers: [
    provideClientHydration(),
    provideAnimationsAsync(),
    provideNativeDateAdapter(),
    DatePipe
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
