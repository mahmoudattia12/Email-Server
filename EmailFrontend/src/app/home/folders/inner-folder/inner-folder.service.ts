import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import { IEmail } from '../../home.component';


@Injectable({
  providedIn: 'root'
})
export class InnerFolderService {
  loginUsername = LogInComponent.currUserName;

  constructor(private http : HttpClient) { }

  getFolder(loginUsername:string, folderName: string) : Observable<IEmail[]>{

    return this.http.get<IEmail[]>("http://localhost:9080/getOneFolder?username=" + loginUsername + "&foldername=" + folderName);
  }

  movetoTrash(folderName:string, email:IEmail[]) : Observable<IEmail[]>{

    return this.http.post<IEmail[]>("http://localhost:9080/moveToTrashFolder?foldername=" + folderName, email);
  }




}
