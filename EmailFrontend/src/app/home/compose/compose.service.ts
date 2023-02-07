import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import { IEmail } from '../home.component';

@Injectable({
  providedIn: 'root'
})

export class ComposeService {

  username = LogInComponent.currUserName;

  constructor(private http : HttpClient) { }

  sendEmail(email:IEmail) : Observable<string>{
    return this.http.post("http://localhost:9080/sendEmail", email, {responseType: "text" });
  }
/////////////////////////////////////////////////////////
  sendWithAttachments(email:IEmail, formData? : FormData) : Observable<string> {
    return this.http.post<string>("http://localhost:9080/sendWithAttachments?email=" + encodeURIComponent(JSON.stringify(email)), formData);
  }
  ///////////////////////////////////////////////////////////////

  movetoDraft(email:IEmail) : Observable<String> {
    return this.http.post<String>("http://localhost:9080/movetoDraft", email);
  }
}