import { SentService } from './sent.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HomeComponent, IAttachment, IEmail } from '../home.component';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import * as $ from 'jquery';

@Component({
  selector: 'app-sent',
  templateUrl: './sent.component.html',
  styleUrls: ['./sent.component.css']
})
export class SentComponent implements OnInit {
  inbox_button!:HTMLButtonElement;
  sent_button!:HTMLButtonElement;
  draft_button!:HTMLButtonElement;
  contacts_button!:HTMLButtonElement;
  folders_button!:HTMLButtonElement;
  trash_button!:HTMLButtonElement;


  public static listOfEmails: IEmail[];
  private listOfCheckboxes: IEmail[];
  private view: string[][] = [];
  private no_of_displayed_emails: number = 0;

  constructor(private server: SentService, private router: Router) {
    LogInComponent.reload = false;
    this.listOfCheckboxes = [];
    SentComponent.listOfEmails = [];
    HomeComponent.currPage = "Sent"
  }

  options_bar!: HTMLDivElement;
  horizontal!: HTMLHRElement;
  tmpbutton!: HTMLButtonElement;
  emails_field_div!: HTMLDivElement;
  tmpButton!: HTMLButtonElement;
  tmpCheck!: HTMLInputElement;
  lightblocker!: HTMLDivElement;
  emailContents!: HTMLDivElement;
  fromField!: HTMLDivElement;
  subjectField!: HTMLDivElement;
  priorityField!: HTMLDivElement;
  timeField!: HTMLDivElement;
  bodyField!: HTMLDivElement;
  attachmentsField!: HTMLDivElement;

  ngOnInit(): void {
    (<HTMLDivElement>document.getElementById('emails-options')).style.display =
      'flex';
    (<HTMLHRElement>document.getElementById('horizontal-line')).style.display =
      'block';

    (<HTMLButtonElement>(
      document.getElementById('move-to-folder-button')
    )).style.display = 'block';
    (<HTMLButtonElement>(
      document.getElementById('restore-button')
    )).style.display = 'none';
    (<HTMLButtonElement>(
      document.getElementById('refresh-button')
    )).style.display = 'block';
    (<HTMLButtonElement>(
      document.getElementById('delete-button')
    )).style.display = 'block';

    (<HTMLDivElement>document.getElementById('filter-div')).style.display =
      'block';

    (<HTMLDivElement>document.getElementById('search-field')).style.display =
      'block';
    (<HTMLDivElement>document.getElementById('sort_select')).style.display =
      '';
    this.select_component_by_color();
    this.server.getSent(LogInComponent.currUserName).subscribe((response: IEmail[]) => {
      SentComponent.listOfEmails = response;
      this.constructView();
      this.listOfCheckboxes = [];
      this.display_mails(this.view);
    })

  }

  constructView() {
    this.view = [];
    for (let i = 0; i < SentComponent.listOfEmails.length; i++) {
      this.view[i] = [];
      this.view[i][0] = SentComponent.listOfEmails[i].receiverNames[0];
      this.view[i][1] = SentComponent.listOfEmails[i].timeSent;
      this.view[i][2] = SentComponent.listOfEmails[i].subject;
      this.view[i][3] = SentComponent.listOfEmails[i].priority;
      console.log('view', this.view);
    }
  }

  sortSent(emails: IEmail[]) {
    SentComponent.listOfEmails = emails;
    this.constructView();
    this.display_mails(this.view);
  }
  filterSent(emails: IEmail[]) {
    SentComponent.listOfEmails = emails;
    this.constructView();
    this.display_mails(this.view);
  }
  searchSent(emails: IEmail[]) {
    SentComponent.listOfEmails = emails;
    this.constructView();
    this.display_mails(this.view);
  }

  display_mails(viewArray: string[][]) {
    this.emails_field_div = <HTMLDivElement>(
      document.getElementById('emails-displayed')
    );
    this.emails_field_div.innerHTML = '';
    for (let i = 0; i < viewArray.length; i++) {
      let tmpUser: string = viewArray[i][0];
      let tmpTime: string = new Date(Number(viewArray[i][1])).toLocaleString();
      let tmpSubject: string = viewArray[i][2];
      let tmpPriority: string = viewArray[i][3];
      console.log('username = ', tmpUser);
      this.emails_field_div.innerHTML += `<div><input type="checkbox" id = "${i}-check"><button id = "${i}-button"><span id = "${i}-user">To: ${tmpUser}</span><span id = "${i}-time">${tmpTime}</span><span id = "${i}-priority">${tmpPriority}</span><span id = "${i}-subject">${tmpSubject}</span></button></div><hr>`;
      this.tmpButton = <HTMLButtonElement>(
        document.getElementById(`${i}-button`)
      );

      this.tmpButton.style.border = 'none';
      this.tmpButton.style.background = 'white';
      this.tmpButton.style.fontSize = '1.1em';
      this.tmpButton.style.cursor = 'pointer';
      this.tmpButton.style.padding = '20px';
      this.tmpButton.style.borderRadius = '10px';
      var ckeck_element!: HTMLInputElement;
      var user_element!: HTMLSpanElement;
      var priority_element!: HTMLSpanElement;
      var time_element!: HTMLSpanElement;
      var subject_element!: HTMLSpanElement;

      ckeck_element = <HTMLInputElement>document.getElementById(`${i}-check`);
      user_element = <HTMLSpanElement>document.getElementById(`${i}-user`);
      priority_element = <HTMLSpanElement>(
        document.getElementById(`${i}-priority`)
      );
      time_element = <HTMLSpanElement>document.getElementById(`${i}-time`);
      subject_element = <HTMLSpanElement>(
        document.getElementById(`${i}-subject`)
      );

      ckeck_element.style.marginRight = '10px';
      ckeck_element.style.cursor = 'pointer';
      ckeck_element.style.width = '25px';
      ckeck_element.style.height = '25px';
      user_element.style.marginRight = '30px';
      time_element.style.marginRight = '30px';
      priority_element.style.marginRight = '30px';
      subject_element.style.fontWeight = 'bold';
    }
    this.no_of_displayed_emails = viewArray.length;
    this.addEvent();
  }

  addEvent() {
    for (let i = 0; i < this.no_of_displayed_emails; i++) {
      this.tmpButton = <HTMLButtonElement>(
        document.getElementById(`${i}-button`)
      );
      this.tmpCheck = <HTMLInputElement>document.getElementById(`${i}-check`);
      console.log(this.tmpCheck.checked);
      this.tmpButton.addEventListener('click', $.proxy(this.popupShow, this));
    }
  }

  popupShow(e: any) {
    var emailNumber: number = Number(
      String(e.target.id).substring(0, String(e.target.id).indexOf('-'))
    );
    console.log(emailNumber);

    this.lightblocker = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.emailContents = <HTMLDivElement>(
      document.getElementById('email-contents')
    );

    this.fromField = <HTMLDivElement>document.getElementById('from-field');
    this.subjectField = <HTMLDivElement>(
      document.getElementById('subject-field')
    );
    this.priorityField = <HTMLDivElement>(
      document.getElementById('priority-field')
    );
    this.timeField = <HTMLDivElement>document.getElementById('time-field');
    this.bodyField = <HTMLDivElement>document.getElementById('body-field');
    this.attachmentsField = <HTMLDivElement>(
      document.getElementById('attchments-field')
    );
    this.attachmentsField.innerHTML = "";
    this.lightblocker.style.display = 'block';
    this.emailContents.style.display = 'block';

    var from: string[] = SentComponent.listOfEmails[emailNumber].receiverNames;
    var subject: string = SentComponent.listOfEmails[emailNumber].subject;
    var priority: string = SentComponent.listOfEmails[emailNumber].priority;
    var time: string = new Date(Number(SentComponent.listOfEmails[emailNumber].timeSent)).toLocaleString()
    var body: string = SentComponent.listOfEmails[emailNumber].body;
    var attachments: IAttachment[] = SentComponent.listOfEmails[emailNumber].attachments;

    this.fromField.innerHTML = "";
    for(let i = 0; i < from.length; i++){
      this.fromField.innerHTML += `<h3>${from[i]}</h3>`;
    }

    // this.fromField.innerHTML = from;
    this.subjectField.innerHTML = subject;
    this.priorityField.innerHTML = priority;
    this.timeField.innerHTML = time;
    this.bodyField.innerHTML = body;

    for (let i = 0; i < attachments.length; i++) {
      this.attachmentsField.innerHTML += `<div><a href = "data:${attachments[i].type};base64,${attachments[i].encodedFile}" download = "${attachments[i].name}"><button>${attachments[i].name}</button></a></div>`;
    }
  }
  closePopUp() {
    this.lightblocker = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.emailContents = <HTMLDivElement>(
      document.getElementById('email-contents')
    );
    this.lightblocker.style.display = 'none';
    this.emailContents.style.display = 'none';
  }

  checkTheBox() {
    for (let i = 0; i < SentComponent.listOfEmails.length; i++) {
      this.tmpCheck = <HTMLInputElement>document.getElementById(`${i}-check`);
      if (this.tmpCheck.checked)
        this.listOfCheckboxes.push(SentComponent.listOfEmails[i]);
    }
  }
  deleteSent() {
    this.checkTheBox();
    if (this.listOfCheckboxes.length > 0) {
      this.server.movetoTrash(this.listOfCheckboxes).subscribe((response: IEmail[]) => {
        this.ngOnInit();
      })
    }
  }

  select_component_by_color(){
    this.inbox_button = <HTMLButtonElement>document.getElementById("inbox-button");
    this.trash_button = <HTMLButtonElement>document.getElementById("trash-button");
    this.sent_button = <HTMLButtonElement>document.getElementById("sent-button");
    this.draft_button = <HTMLButtonElement>document.getElementById("draft-button");
    this.contacts_button = <HTMLButtonElement>document.getElementById("contacts-button");
    this.folders_button = <HTMLButtonElement>document.getElementById("folders-button");
    this.sent_button.style.backgroundColor = "rgba(128, 128, 128, 0.3)";
    this.inbox_button.style.backgroundColor = "transparent";
    this.trash_button.style.backgroundColor = "transparent";
    this.draft_button.style.backgroundColor = "transparent";
    this.contacts_button.style.backgroundColor = "transparent";
    this.folders_button.style.backgroundColor = "transparent";
  }

  moveToFolder(folderName : string){
    this.checkTheBox();
    if(this.listOfCheckboxes.length > 0){
      this.server.moveToFolder(LogInComponent.currUserName, folderName, this.listOfCheckboxes).subscribe((response: string) => {
        alert(response);
        this.ngOnInit();
      })
    }
  }
}
