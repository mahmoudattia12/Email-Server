import { IContact } from './contacts/contacts.component';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LogInComponent } from '../log-in/log-in.component';
import { HomeComponent, IEmail } from './home.component';


@Injectable({
    providedIn: 'root'
})
export class HomeService {
    loginUsername = LogInComponent.currUserName;
    filterOption = HomeComponent.filterOption
    filterkey = HomeComponent.filterkey
    sortOption = HomeComponent.sortOption;
    sortOrder = HomeComponent.sortOrder;
    searchkey = HomeComponent.searchkey;
    pageIndicator = HomeComponent.currPage;
    
    constructor(private http: HttpClient) { }
    
    
    sortInbox(loginUsername: string, sortOption: string, sortOrder: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/sortInbox?username=" + loginUsername + "&target=" + sortOption + "&ascending=" + sortOrder);
    }
    filterInbox(loginUsername: string, filterOption: string, filterkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/filterInbox?username=" + loginUsername + "&feature=" + filterOption + "&target=" + filterkey);
    }
    searchInbox(loginUsername: string, searchkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/searchInbox?username=" + loginUsername + "&target=" + searchkey);
    }

    sortSent(loginUsername: string, sortOption: string, sortOrder: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/sortSent?username=" + loginUsername + "&target=" + sortOption + "&ascending=" + sortOrder);
    }
    filterSent(loginUsername: string, filterOption: string, filterkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/filterSent?username=" + loginUsername + "&feature=" + filterOption + "&target=" + filterkey);
    }
    searchSent(loginUsername: string, searchkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/searchSent?username=" + loginUsername + "&target=" + searchkey);
    }

    sortDraft(loginUsername: string, sortOption: string, sortOrder: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/sortDraft?username=" + loginUsername + "&target=" + sortOption + "&ascending=" + sortOrder);
    }
    filterDraft(loginUsername: string, filterOption: string, filterkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/filterDraft?username=" + loginUsername + "&feature=" + filterOption + "&target=" + filterkey);
    }
    searchDraft(loginUsername: string, searchkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/searchDraft?username=" + loginUsername + "&target=" + searchkey);
    }

    sortTrash(loginUsername: string, sortOption: string, sortOrder: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/sortTrash?username=" + loginUsername + "&target=" + sortOption + "&ascending=" + sortOrder);
    }
    filterTrash(loginUsername: string, filterOption: string, filterkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/filterTrash?username=" + loginUsername + "&feature=" + filterOption + "&target=" + filterkey);
    }
    searchTrash(loginUsername: string, searchkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/searchTrash?username=" + loginUsername + "&target=" + searchkey);
    }

    sortInnerFolder(loginUsername: string, foldername: string, sortOption: string, sortOrder: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/sortFolder?username=" + loginUsername + "&target=" + sortOption + "&ascending=" + sortOrder + "&foldername=" + foldername);
    }
    filterInnerFolder(loginUsername: string, foldername: string, filterOption: string, filterkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/filterFolder?username=" + loginUsername + "&feature=" + filterOption + "&target=" + filterkey + "&foldername=" + foldername);
    }
    searchInnerFolder(loginUsername: string, foldername: string, searchkey: string): Observable<IEmail[]> {
        return this.http.get<IEmail[]>("http://localhost:9080/searchFolder?username=" + loginUsername + "&target=" + searchkey + "&foldername=" + foldername);
    }

    sortContacts(loginUsername: string, sortOrder: string): Observable<IContact[]> {
        return this.http.get<IContact[]>("http://localhost:9080/sortContacts?username=" + loginUsername + "&ascending=" + sortOrder);
    }
    searchContacts(loginUsername: string, searchkey: string): Observable<IContact[]> {
        return this.http.get<IContact[]>("http://localhost:9080/searchContacts?username=" + loginUsername + "&target=" + searchkey);
    }

    // sortFolder(loginUsername: string, folderName: string, sortOption: string, sortOrder: string): Observable<IEmail[]> {
    //     return this.http.get<IEmail[]>("http://localhost:9080/sortFolder?username=" + loginUsername + "&foldername=" + folderName + "&target=" + sortOption + "&ascending=" + sortOrder);
    // }
}