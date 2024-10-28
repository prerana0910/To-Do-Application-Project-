import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject, catchError } from 'rxjs';
import { ToDo } from '../../models/Task';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private hc:HttpClient) { }
  public addTask(task: any, token: any) {
    console.log('service:' );
    console.log(task);
    console.log('token');
    console.log(token);
    let tokenType = "Bearer " + token;
    const headers = new HttpHeaders().set("Authorization", tokenType);
    return this.hc.post<any>('http://localhost:8090/api/v3/addToDoTask', task, { headers });
  }
  public getUserTask(token : any) : Observable<any[]> {
    let tokenType = "Bearer " + token;
    const headers = new HttpHeaders().set("Authorization", tokenType);
    return this.hc.get<any[]>("http://localhost:8090/api/v3/getAllToDoTask", {headers});
  }
  public updateTask(task:any, token:any){
    let tokenType = "Bearer " + token;
    const headers = new HttpHeaders().set("Authorization", tokenType);
    return this.hc.put<any>('http://localhost:8090/api/v3/updateToDoTask', task, { headers });
  }

  public getAParticluarTask( token:any ,taskName:any){
    let tokenType = "Bearer " + token;
    const headers = new HttpHeaders().set("Authorization", tokenType);
    return this.hc.get<any>('http://localhost:8090/api/v3/getOneToDoTask/'+ taskName, { headers });

  }
  public updateTaskCompletion(taskName: string, completed: boolean, token: any): Observable<any> {
    const url = `http://localhost:8090/api/v3/updateToDoTask/${taskName}`;
    const tokenType = "Bearer " + token;
    const headers = new HttpHeaders().set("Authorization", tokenType);
    return this.hc.put<any>(url, { iscompleted: completed }, { headers });
  }
  public deleteTask(task:any, token:any){
    let tokenType = "Bearer " + token;
    const headers = new HttpHeaders().set("Authorization", tokenType);
    const options = { headers, body: task };
    return this.hc.delete<any>('http://localhost:8090/api/v3/deleteToDoTask', options);
  }
  private taskNameSource = new BehaviorSubject<string>('');
  currentTaskName = this.taskNameSource.asObservable();

  changeTaskName(taskName: string) {
    this.taskNameSource.next(taskName);
  }
  private markAsCompletedSubject = new Subject<void>();

  // Observable for mark as completed event
  markAsCompleted$ = this.markAsCompletedSubject.asObservable();

  // Method to trigger mark as completed event
  triggerMarkAsCompleted() {
    this.markAsCompletedSubject.next();
  }
}
