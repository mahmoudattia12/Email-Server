import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import { IContact } from './contacts.component';

@Injectable({
  providedIn: 'root'
})

export class ContactsService {

  username = LogInComponent.currUserName;

  constructor(private http : HttpClient) { }

  getContacts(username:string) : Observable<IContact[]>{

    return this.http.get<IContact[]>("http://localhost:9080/getContacts?username=" + username);
  }

  addContact(username:string, contact: string, emailsList: string[]) : Observable<string>{
    return this.http.post("http://localhost:9080/addContact?username=" + username + "&contactName=" + contact, emailsList,{responseType:"text"})
  }

  removeContact(username:string,selectedContacts:IContact[]) : Observable<String> {
    return this.http.delete("http://localhost:9080/removeContact?username=" + username , {body: selectedContacts, responseType: "text"});
  }


}
