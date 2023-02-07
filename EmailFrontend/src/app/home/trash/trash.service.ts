import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import { IEmail } from '../home.component';

@Injectable({
  providedIn: 'root'
})

export class TrashService {

  username = LogInComponent.currUserName;

  constructor(private http : HttpClient) {
    LogInComponent.reload = false;
   }

  getTrash(loginUsername:string) : Observable<IEmail[]>{

    return this.http.get<IEmail[]>("http://localhost:9080/getTrash?username=" + loginUsername);
  }

  delete(emails:IEmail[]) : Observable<IEmail[]> {
    return this.http.delete<IEmail[]>("http://localhost:9080/delete", {body: emails});
  }

  restore(emails:IEmail[]) : Observable<IEmail[]>{
    return this.http.post<IEmail[]>("http://localhost:9080/restore" , emails)
  }
}