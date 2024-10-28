import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { Observable, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private hc:HttpClient) { }
  public registerUser(newUser : User) : Observable<User>{
    return this.hc.post<User>("http://localhost:8090/api/v1/register", newUser);
  }
  public loginUser(loggedInUser: User) {
    return this.hc.post<{ Token: string }>("http://localhost:8090/api/v2/login", loggedInUser, { responseType: 'json' })
      .pipe(
        map(response => response.Token),
        tap(token => sessionStorage.setItem("Token", token))
      );
  }
  public getUserInfo(token:any) : Observable<User>{
    let tokenType = "Bearer " + token;
    const headers = new HttpHeaders().set("Authorization", tokenType);
    return this.hc.get<User>("http://localhost:8090/api/v3/getUserDetails", {headers});
  }
}
