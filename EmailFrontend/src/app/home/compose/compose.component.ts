import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { HomeComponent } from './../home.component';
import { Component, OnInit } from '@angular/core';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import { IAttachment, IEmail } from '../home.component';
import { ComposeService } from './compose.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-compose',
  templateUrl: './compose.component.html',
  styleUrls: ['./compose.component.css'],
})
export class ComposeComponent implements OnInit {
  //private reciever: string;
  public static sentEmail: IEmail;
  private attachments: IAttachment[];
  private no_of_recievers: number;
  private attachForm = new FormData();
  more_recievers_div!: HTMLDivElement;
  tmpinput!: HTMLInputElement;

  constructor(private server: ComposeService, private router: Router) {
    //this.reciever = "";
    this.no_of_recievers = 1;
    this.attachments = [];
    if (LogInComponent.reload == false) {
      ComposeComponent.sentEmail = {
        senderName: '',
        receiverNames: [],
        owner: '',
        subject: '',
        body: '',
        timeSent: '',
        priority: '',
        emailID: '',
        emailType: '',
        attachments: [],
      };
    }
    LogInComponent.reload = true;
    HomeComponent.currPage = 'SendEmail';
  }

  ngOnInit(): void {
  }

  getAttachments(attach: any) {
    var files: File[];
    files = attach.files;

    var temp = new FormData();
    for (let i = 0; i < files.length; i++) {
      temp.append('file', files[i]);
    }
    this.attachForm = temp;
    if (files.length == 0) {
      
      this.attachForm.append('file', '');
      
    }
    console.log("attachForm",this.attachForm);
  }

  sendEmail() {
    const now = new Date();
    ComposeComponent.sentEmail.timeSent = String(now.getTime());
    ComposeComponent.sentEmail.body = (<HTMLInputElement>(
      document.getElementById('message')
    )).value;
    ComposeComponent.sentEmail.subject = (<HTMLInputElement>(
      document.getElementById('subject')
    )).value;
    this.getAllRecievers();
    ComposeComponent.sentEmail.senderName = LogInComponent.currUserName;
    ComposeComponent.sentEmail.owner = LogInComponent.currUserName;
    ComposeComponent.sentEmail.priority = (<HTMLSelectElement>(
      document.getElementById('priority_select')
    )).value;
    //send to back here with or without attachments
    if (!this.attachForm.get('file')) {
      this.server
        .sendEmail(ComposeComponent.sentEmail)
        .subscribe((response: String) => {
          alert(response);
        });
    } else {
      this.server
        .sendWithAttachments(ComposeComponent.sentEmail, this.attachForm)
        .subscribe(() => {
        });
    }
    this.router.navigate(['/home/inbox']);
  }
  moveDraft() {
    const now = new Date();
    ComposeComponent.sentEmail.timeSent = String(now.getTime());
    ComposeComponent.sentEmail.body = (<HTMLInputElement>(
      document.getElementById('message')
    )).value;
    ComposeComponent.sentEmail.subject = (<HTMLInputElement>(
      document.getElementById('subject')
    )).value;
    this.getAllRecievers();
    ComposeComponent.sentEmail.senderName = LogInComponent.currUserName;
    ComposeComponent.sentEmail.owner = LogInComponent.currUserName;
    ComposeComponent.sentEmail.priority = (<HTMLSelectElement>(
      document.getElementById('priority_select')
    )).value;
    this.server
      .movetoDraft(ComposeComponent.sentEmail)
      .subscribe((response: String) => {
        alert(response);
      });
    this.router.navigate(['/home/inbox']);
  }

  addMoreReciever() {
    this.getAllRecievers();
    this.more_recievers_div = <HTMLDivElement>(
      document.getElementById('more-recievers')
    );
    this.no_of_recievers++;
    this.more_recievers_div.innerHTML += `<div id="reciever-${this.no_of_recievers}"><span>To:</span><input type="text" id="${this.no_of_recievers}-reciever"/></div>`;
    this.more_recievers_div = <HTMLDivElement>(
      document.getElementById(`reciever-${this.no_of_recievers}`)
    );
    this.tmpinput = <HTMLInputElement>(
      document.getElementById(`${this.no_of_recievers}-reciever`)
    );
    this.more_recievers_div.style.marginBottom = '30px';
    this.more_recievers_div.style.border = ' 1px solid black';
    this.more_recievers_div.style.width = '80%';
    this.more_recievers_div.style.padding = '10px';
    this.tmpinput.style.fontSize = '1.2em';
    this.tmpinput.style.width = '80%';
    this.tmpinput.style.border = 'none';
    this.displayRecName();
    // console.log(this.no_of_recievers);
  }

  getAllRecievers() {
    for (let i = 1; i <= this.no_of_recievers; i++) {
      ComposeComponent.sentEmail.receiverNames.push(
        (<HTMLInputElement>document.getElementById(`${i}-reciever`)).value
      );
    }
    // console.log(ComposeComponent.sentEmail.receiverNames);
  }

  displayRecName() {
    for (let i = 1; i < this.no_of_recievers; i++) {
      this.tmpinput = <HTMLInputElement>(
        document.getElementById(`${i}-reciever`)
      );
      this.tmpinput.value = ComposeComponent.sentEmail.receiverNames[i - 1];
    }
    ComposeComponent.sentEmail.receiverNames = [];
  }
}
