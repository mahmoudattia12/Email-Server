import { IContact } from './contacts/contacts.component';
import { LogInComponent } from './../log-in/log-in.component';
import { TrashComponent } from './trash/trash.component';
import { DraftComponent } from './draft/draft.component';
import { SentComponent } from './sent/sent.component';
import { InboxComponent } from './inbox/inbox.component';
import { HomeService } from './home.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { FoldersComponent } from './folders/folders.component';
import { InnerFolderComponent } from './folders/inner-folder/inner-folder.component';
import { ContactsComponent } from './contacts/contacts.component';

export interface IAttachment {
  name: string;
  type: string;
  encodedFile: string;
}

export interface IEmail {
  senderName: string;
  receiverNames: string[];
  owner: string;
  subject: string;
  body: string;
  timeSent: string;
  priority: string;
  emailID: string;
  emailType: string;
  attachments: IAttachment[];
}

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})



export class HomeComponent implements OnInit {
  tmpDiv!: HTMLDivElement;
  tmp2Div!: HTMLDivElement;
  tmpinput!: HTMLInputElement;
  welcoming_header!: HTMLHeadingElement;
  constructor(private router: Router, private server: HomeService,
    private inbox: InboxComponent, private sent: SentComponent,
    private draft: DraftComponent, private trash: TrashComponent, private folders: FoldersComponent, private innerFolder: InnerFolderComponent, private contacts:ContactsComponent) {
  }

  public static filterOption: string = "";
  public static filterkey: string = "";
  public static sortOption: string = "";
  public static sortOrder: string = "true";
  public static searchkey: string = "";
  public static currPage: string;
  path: string = "";
  private originalFolderName: string = "";

  ngOnInit(): void {
    this.welcoming_header = <HTMLHeadingElement>document.getElementById("userName");
    this.welcoming_header.innerText = `${LogInComponent.currUserName}`;
    this.welcoming_header.style.wordWrap = "break-word";
    this.router.navigate(['/home/inbox']);
  }


  refresh() {
    if (HomeComponent.currPage == "Inbox") {
      this.inbox.ngOnInit();
    }
    else if (HomeComponent.currPage == "Sent") {
      this.sent.ngOnInit();
    }
    else if (HomeComponent.currPage == "Draft") {
      this.draft.ngOnInit();
    }
    else if (HomeComponent.currPage == "Trash") {
      this.trash.ngOnInit();
    }
    else if (HomeComponent.currPage == "InnerFolder") {
      this.innerFolder.ngOnInit();
    }
    else if (HomeComponent.currPage == "Contacts") {
      this.contacts.ngOnInit();
    }
  }

  delete() {
    if (HomeComponent.currPage == "Inbox") {
      this.inbox.deleteInbox();
    }
    else if (HomeComponent.currPage == "Sent") {
      this.sent.deleteSent();
    }
    else if (HomeComponent.currPage == "Draft") {
      this.draft.deleteDraft();
    }
    else if (HomeComponent.currPage == "Trash") {
      this.trash.deleteTrash();
    }
    else if (HomeComponent.currPage == "InnerFolder") {
      this.innerFolder.deleteInnerFolder();
    }
    else if (HomeComponent.currPage == "Contacts") {
      this.contacts.deleteContact();
    }
  }

  filter() {
    var selector = (<HTMLSelectElement>document.getElementById("Filter-select"));
    HomeComponent.filterOption = selector.options[selector.selectedIndex].text;
    HomeComponent.filterkey = (<HTMLInputElement>document.getElementById("filter-input")).value;

    if (HomeComponent.currPage == "Inbox") {
      this.server.filterInbox(LogInComponent.currUserName, HomeComponent.filterOption, HomeComponent.filterkey)
        .subscribe((response: IEmail[]) => {
          this.inbox.filterInbox(response);
        })
    }
    else if (HomeComponent.currPage == "Sent") {
      this.server.filterSent(LogInComponent.currUserName, HomeComponent.filterOption, HomeComponent.filterkey)
        .subscribe((response: IEmail[]) => {
          this.sent.filterSent(response);
        })
    }
    else if (HomeComponent.currPage == "Draft") {
      this.server.filterDraft(LogInComponent.currUserName, HomeComponent.filterOption, HomeComponent.filterkey)
        .subscribe((response: IEmail[]) => {
          this.draft.filterDraft(response);
        })
    }
    else if (HomeComponent.currPage == "Trash") {
      this.server.filterTrash(LogInComponent.currUserName, HomeComponent.filterOption, HomeComponent.filterkey)
        .subscribe((response: IEmail[]) => {
          this.trash.filterTrash(response);
        })
    }
    else if (HomeComponent.currPage == "InnerFolder") {   //check the name of the folder
      this.server.filterInnerFolder(LogInComponent.currUserName, String(FoldersComponent.new_to_original_name.get(FoldersComponent.folderSelected)), HomeComponent.filterOption, HomeComponent.filterkey)
        .subscribe((response: IEmail[]) => {
          this.innerFolder.filterInnerFolder(response);
        })
    }
  }

  sort() {
    var selector1 = (<HTMLSelectElement>document.getElementById("sort_select"));
    HomeComponent.sortOption = selector1.options[selector1.selectedIndex].text;

    var selector2 = (<HTMLSelectElement>document.getElementById("order_select"));
    var sortType = selector2.options[selector2.selectedIndex].text

    if (sortType == "Decendingly") {
      HomeComponent.sortOrder = "false";
    } else {
      HomeComponent.sortOrder = "true";
    }

    if (HomeComponent.currPage == "Inbox") {
      this.server.sortInbox(LogInComponent.currUserName, HomeComponent.sortOption, HomeComponent.sortOrder)
        .subscribe((response: IEmail[]) => {
          this.inbox.sortInbox(response);
        })
    }
    else if (HomeComponent.currPage == "Sent") {
      this.server.sortSent(LogInComponent.currUserName, HomeComponent.sortOption, HomeComponent.sortOrder)
        .subscribe((response: IEmail[]) => {
          this.sent.sortSent(response);
        })
    }
    else if (HomeComponent.currPage == "Draft") {
      this.server.sortDraft(LogInComponent.currUserName, HomeComponent.sortOption, HomeComponent.sortOrder)
        .subscribe((response: IEmail[]) => {
          this.draft.sortDraft(response);
        })
    }
    else if (HomeComponent.currPage == "Trash") {
      this.server.sortTrash(LogInComponent.currUserName, HomeComponent.sortOption, HomeComponent.sortOrder)
        .subscribe((response: IEmail[]) => {
          this.trash.sortTrash(response);
        })
    }
    else if (HomeComponent.currPage == "InnerFolder") {
      this.server.sortInnerFolder(LogInComponent.currUserName,String(FoldersComponent.new_to_original_name.get(FoldersComponent.folderSelected)), HomeComponent.sortOption, HomeComponent.sortOrder)
        .subscribe((response: IEmail[]) => {
          this.innerFolder.sortInnerFolder(response);
        })
    }
    else if (HomeComponent.currPage == "Contacts") {
      this.server.sortContacts(LogInComponent.currUserName, HomeComponent.sortOrder)
        .subscribe((response: IContact[]) => {
          console.log(response);
          this.contacts.sortContacts(response);
        })
    }
  }
  search() {
    HomeComponent.searchkey = (<HTMLInputElement>document.getElementById("search-input")).value
    if (HomeComponent.currPage == "Inbox") {
      this.server.searchInbox(LogInComponent.currUserName, HomeComponent.searchkey)
        .subscribe((response: IEmail[]) => {
          this.inbox.searchInbox(response);
        })
    }
    else if (HomeComponent.currPage == "Sent") {
      this.server.searchSent(LogInComponent.currUserName, HomeComponent.searchkey)
        .subscribe((response: IEmail[]) => {
          this.sent.searchSent(response);
        })
    }

    else if (HomeComponent.currPage == "Draft") {
      this.server.searchDraft(LogInComponent.currUserName, HomeComponent.searchkey)
        .subscribe((response: IEmail[]) => {
          this.draft.searchDraft(response);
        })
    }
    else if (HomeComponent.currPage == "Trash") {
      this.server.searchTrash(LogInComponent.currUserName, HomeComponent.searchkey)
        .subscribe((response: IEmail[]) => {
          this.trash.searchTrash(response);
        })
    }
    else if (HomeComponent.currPage == "InnerFolder") {
      this.server.searchInnerFolder(LogInComponent.currUserName,String(FoldersComponent.new_to_original_name.get(FoldersComponent.folderSelected)), HomeComponent.searchkey)
        .subscribe((response: IEmail[]) => {
          this.innerFolder.searchInnerFolder(response);
        })
    }
    else if (HomeComponent.currPage == "Contacts") {

      this.server.searchContacts(LogInComponent.currUserName, HomeComponent.searchkey)
        .subscribe((response: IContact[]) => {
          console.log(response);
          this.contacts.searchContacts(response);
        })
    }
  }


  gotoinbox() {
    this.router.navigate(['/home/inbox']);
  }
  gotosent() {
    this.router.navigate(['/home/sent']);
  }
  gotodraft() {
    this.router.navigate(['/home/draft']);
  }
  gototrash() {
    this.router.navigate(['/home/trash']);
  }
  gotoCompose() {
    this.router.navigate(['/home/compose']);
  }
  gotofolders() {
    this.router.navigate(['/home/folders']);
  }
  gotocontacts() {
    this.router.navigate(['/home/contacts']);
  }

  addNewFolderClicked() {
    FoldersComponent.createNewFolderClicked = true;
    if (HomeComponent.currPage == "FoldersPage") this.folders.displayCraateFolderWindow();
    else {
      this.router.navigate(['/home/folders']);
    }
  }

  addNewContactClicked(){
    ContactsComponent.createNewContactClicked = true;
    if (HomeComponent.currPage == "Contacts") this.contacts.displayCraateContactWindow();
    else{
      // this.contacts.displayCraateContactWindow();
      this.router.navigate(['/home/contacts']);
    }
  }

  pop_move_to_folder() {
    this.tmpinput = <HTMLInputElement>document.getElementById("folder-entered-name");
    this.tmpinput.value = "";
    this.tmpDiv = <HTMLDivElement>document.getElementById("move-to-folder-window");
    this.tmp2Div = <HTMLDivElement>document.getElementById("light-blocker");
    this.tmpDiv.style.display = "block";
    this.tmp2Div.style.display = "block";
  }

  readFolderName() {
    var folderName: string = (<HTMLInputElement>document.getElementById("folder-entered-name")).value;

    var folderExists: boolean = false;
    for (let i = 0; i < FoldersComponent.listOfFolders.length; i++) {
      if (folderName == FoldersComponent.listOfFolders[i]) {
        folderExists = true;
        this.originalFolderName = String(FoldersComponent.new_to_original_name.get(folderName));
      }
    }
    if (!folderExists) alert("no such folder exists")
    else {
      //this.innerFolder.checkTheBox();
      this.folderMoveAction();
      this.close_move_to_folder();
    }
  }
  folderMoveAction() {
    if (HomeComponent.currPage == "Inbox") {
      this.inbox.moveToFolder(this.originalFolderName);
    }
    else if (HomeComponent.currPage == "Sent") {
      this.sent.moveToFolder(this.originalFolderName);
    }
    else if (HomeComponent.currPage == "Draft") {
      this.draft.moveToFolder(this.originalFolderName);
    }
  }
  close_move_to_folder() {
    this.tmpDiv = <HTMLDivElement>document.getElementById("move-to-folder-window");
    this.tmp2Div = <HTMLDivElement>document.getElementById("light-blocker");
    this.tmpDiv.style.display = "none";
    this.tmp2Div.style.display = "none";
  }
  restore_emails_from_trash(){
    this.trash.restore();
  }
}
