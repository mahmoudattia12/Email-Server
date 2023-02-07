import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import { IEmail } from '../home.component';

@Injectable({
  providedIn: 'root'
})

export class DraftService {

  username = LogInComponent.currUserName;

  constructor(private http : HttpClient) { }

  getDraft(loginUsername:string) : Observable<IEmail[]>{

    return this.http.get<IEmail[]>("http://localhost:9080/getDraft?username=" + loginUsername);
  }

  movetoTrash(emails:IEmail[]) : Observable<IEmail[]> {
    return this.http.post<IEmail[]>("http://localhost:9080/movetoTrashDraft", emails);
  }

  moveToFolder(loginUsername: string, folderName: string, emails:IEmail[]) : Observable<string>{
    return this.http.post("http://localhost:9080/moveToFolder?username=" + loginUsername + "&foldername=" + folderName, emails, {responseType : "text"})
  }
}