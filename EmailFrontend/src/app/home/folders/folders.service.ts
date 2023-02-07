import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogInComponent } from 'src/app/log-in/log-in.component';



@Injectable({
  providedIn: 'root'
})
export class FolderService {
  loginUsername = LogInComponent.currUserName;

  constructor(private http : HttpClient) { }

  removeUserFolder(loginUsername:string,folderName:string) : Observable<string>{
    return this.http.delete("http://localhost:9080/removeFolder?username=" + loginUsername + "&foldername=" + folderName, {responseType : "text"});
  }

  addUserFolder(loginUsername:string,folderName:string) : Observable<String>{
    return this.http.post("http://localhost:9080/addFolder?username=" + loginUsername , folderName, {responseType : "text"});
  }

}
