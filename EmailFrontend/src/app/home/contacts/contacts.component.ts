import { LogInComponent } from './../../log-in/log-in.component';
import { Component, OnInit } from '@angular/core';
import { ContactsService } from './contacts.service';
import { Router } from '@angular/router';
import { HomeComponent } from '../home.component';
import * as $ from 'jquery';
import { InboxComponent } from '../inbox/inbox.component';

export interface IContact {
  name: string;
  emails: string[];
}

export class Contacts implements IContact {
  name: string;
  emails: string[];

  constructor(username: string, emails: string[]) {
    this.name = username;
    this.emails = emails;
  }
}

@Component({
  selector: 'app-contacts',
  templateUrl: './contacts.component.html',
  styleUrls: ['./contacts.component.css'],
})
export class ContactsComponent implements OnInit {
  inbox_button!: HTMLButtonElement;
  sent_button!: HTMLButtonElement;
  draft_button!: HTMLButtonElement;
  contacts_button!: HTMLButtonElement;
  folders_button!: HTMLButtonElement;
  trash_button!: HTMLButtonElement;
  more_emails_div!: HTMLDivElement;
  tmpinput!: HTMLInputElement;
  lightBlockerField!: HTMLDivElement;
  createContactWindow!: HTMLDivElement;
  emails_field_div!: HTMLDivElement;
  tmpButton!: HTMLButtonElement;
  message_div!: HTMLDivElement;
  tmpEditButton!: HTMLButtonElement;
  tmpCheck!: HTMLInputElement;
  contactName_field!: HTMLDivElement;
  contactEmails_field!: HTMLDivElement;
  contactContents_window!: HTMLDivElement;

  public static listOfContacts: IContact[];
  private listOfCheckboxes: IContact[];
  private view: string[][] = [];
  private current_contact_emails: string[];
  private current_cantact_number_of_emails: number;
  private current_contact_edited_number_of_emails: number = 0;
  private no_of_displayed_emails: number = 0;
  public static createNewContactClicked: boolean = false;
  public static coming_from_contacts: boolean = false;
  //InboxComponent.pre_edited_username: string = '';
  //private Emails: string[] = [];
  
  constructor(private server: ContactsService, private router: Router) {
    HomeComponent.currPage = 'Contacts';
    this.current_contact_emails = [];
    this.current_cantact_number_of_emails = 1;
    //this. current_contact_edited_number_of_emails = 0;
    LogInComponent.reload = false;
    ContactsComponent.listOfContacts = [];
    this.listOfCheckboxes = [];
    this.view = [];
    console.log("inner the constructor", InboxComponent.Emails.length);
    InboxComponent.Emails = [];
    console.log("inner the constructor", InboxComponent.Emails.length);
    //InboxComponent.arrayCounter = [];
  }

  ngOnInit(): void {
    (<HTMLDivElement>document.getElementById('emails-options')).style.display =
      'flex';
    (<HTMLHRElement>document.getElementById('horizontal-line')).style.display =
      'block';

    (<HTMLButtonElement>(
      document.getElementById('move-to-folder-button')
    )).style.display = 'none';
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
      'none';

    (<HTMLDivElement>document.getElementById('search-field')).style.display =
      'block';
    (<HTMLDivElement>document.getElementById('sort_select')).style.display =
      'none';

    this.select_component_by_color();
    this.server
      .getContacts(LogInComponent.currUserName)
      .subscribe((response: IContact[]) => {
        if (response == null) {
          ContactsComponent.listOfContacts = [];
          this.view = [];
        } else {
          ContactsComponent.listOfContacts = response;
          this.constructView();
        }
        this.listOfCheckboxes = [];
        this.display_mails(this.view);
      });
    if (ContactsComponent.createNewContactClicked) {
      this.displayCraateContactWindow();
    }
  }

  constructView() {
    this.view = [];
    for (let i = 0; i < ContactsComponent.listOfContacts.length; i++) {
      this.view[i] = [];
      this.view[i][0] = ContactsComponent.listOfContacts[i].name;
      this.view[i][1] = ContactsComponent.listOfContacts[i].emails[0];
    }
  }

  display_mails(viewArray: string[][]) {
    this.emails_field_div = <HTMLDivElement>(
      document.getElementById('emails-displayed')
    );
    this.emails_field_div.innerHTML = '';
    for (let i = 0; i < viewArray.length; i++) {
      let tmpUser: string = viewArray[i][0];
      let tmpFirstMail: string = viewArray[i][1];
      this.emails_field_div.innerHTML += `<div id = "${i}-contact-container"><div id="${i}-contact-div"><input type="checkbox" id = "${i}-check"><button id = "${i}-button"><h4 id = "${i}-usernameHeading">Name:</h4><span id = "${i}-user">${tmpUser}</span><h4 id = "${i}-emailHeading">Profile:</h4><span id = "${i}-firstMail">${tmpFirstMail}</span></button></div><button id = "${i}-editButton">Edit Contact</button></div><hr>`;
      this.tmpButton = <HTMLButtonElement>(
        document.getElementById(`${i}-button`)
      );
      this.tmpEditButton = <HTMLButtonElement>(
        document.getElementById(`${i}-editButton`)
      );
      this.message_div = <HTMLDivElement>(
        document.getElementById(`${i}-contact-div`)
      );

      this.message_div.style.display = 'flex';
      this.message_div.style.alignItems = 'center';
      // this.message_div.style.justifyContent = "flexStart"

      this.tmpButton.style.border = 'none';
      this.tmpButton.style.background = 'white';
      this.tmpButton.style.fontSize = '1.1em';
      this.tmpButton.style.cursor = 'pointer';
      this.tmpButton.style.borderRadius = '10px';
      // this.tmpButton.style.width = "800px"

      this.tmpEditButton.style.border = 'none';
      this.tmpEditButton.style.background = 'lightgreen';
      this.tmpEditButton.style.color = 'black';
      this.tmpEditButton.style.fontSize = '1.1em';
      this.tmpEditButton.style.cursor = 'pointer';
      this.tmpEditButton.style.padding = '5px';
      this.tmpEditButton.style.borderRadius = '10px';

      var ckeck_element!: HTMLInputElement;
      var user_element!: HTMLSpanElement;
      var firstEmail_element!: HTMLSpanElement;
      var tmpUsernameHeading!: HTMLHeadingElement;
      var tmpEmailHeading!: HTMLHeadingElement;
      var contact_container!: HTMLDivElement;

      ckeck_element = <HTMLInputElement>document.getElementById(`${i}-check`);
      user_element = <HTMLSpanElement>document.getElementById(`${i}-user`);
      firstEmail_element = <HTMLSpanElement>(
        document.getElementById(`${i}-firstMail`)
      );
      tmpUsernameHeading = <HTMLHeadingElement>(
        document.getElementById(`${i}-usernameHeading`)
      );
      tmpEmailHeading = <HTMLHeadingElement>(
        document.getElementById(`${i}-emailHeading`)
      );
      contact_container = <HTMLDivElement>(
        document.getElementById(`${i}-contact-container`)
      );

      tmpUsernameHeading.style.display = 'inline-block';
      tmpUsernameHeading.style.marginRight = '10px';

      tmpEmailHeading.style.display = 'inline-block';
      tmpEmailHeading.style.marginRight = '10px';

      ckeck_element.style.marginRight = '10px';
      ckeck_element.style.cursor = 'pointer';
      ckeck_element.style.width = '25px';
      ckeck_element.style.height = '25px';

      user_element.style.marginRight = '30px';

      firstEmail_element.style.marginRight = '30px';

      contact_container.style.display = 'flex';
      contact_container.style.justifyContent = 'space-between';
      contact_container.style.width = '900px';
    }
    this.no_of_displayed_emails = viewArray.length;
    this.addEvent();
    this.addEvent_edit();
  }

  addEvent() {
    for (let i = 0; i < this.no_of_displayed_emails; i++) {
      this.tmpButton = <HTMLButtonElement>(
        document.getElementById(`${i}-button`)
      );
      this.tmpCheck = <HTMLInputElement>document.getElementById(`${i}-check`);
      this.tmpButton.addEventListener('click', $.proxy(this.popupShow, this));
    }
  }

  addEvent_edit() {
    for (let i = 0; i < this.no_of_displayed_emails; i++) {
      this.tmpButton = <HTMLButtonElement>(
        document.getElementById(`${i}-editButton`)
      );
      this.tmpCheck = <HTMLInputElement>document.getElementById(`${i}-check`);
      this.tmpButton.addEventListener('click', $.proxy(this.popupEdit, this));
    }
  }

  popupEdit(e: any) {
    InboxComponent.arrayCounter = [];
    InboxComponent.Emails = [];
    InboxComponent.pre_edited_username = "";

    var emailNumber: number = Number(
      String(e.target.id).substring(0, String(e.target.id).indexOf('-'))
    );

    this.lightBlockerField = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.createContactWindow = <HTMLDivElement>(
      document.getElementById('edit-contact-window')
    );
    this.lightBlockerField.style.display = 'block';
    this.createContactWindow.style.display = 'block';

    InboxComponent.Emails = [];
    console.log("email number",emailNumber)
    console.log("list of contacts",ContactsComponent.listOfContacts[emailNumber]);
    for(let i = 0; i < ContactsComponent.listOfContacts[emailNumber].emails.length; i++){
      console.log("conter",i)
      
      InboxComponent.Emails[i] = ContactsComponent.listOfContacts[emailNumber].emails[i];
      this.setCount(i);
    }
    console.log("Emails:" , InboxComponent.Emails);
    console.log("array counter:" , InboxComponent.arrayCounter);
    let username: string = ContactsComponent.listOfContacts[emailNumber].name;
    InboxComponent.pre_edited_username = username; //is it object?
    (<HTMLInputElement>document.getElementById('edited-new-name')).value =
      username;
    (<HTMLInputElement>document.getElementById('0-edited-email')).value =
      InboxComponent.Emails[0];
    this.more_emails_div = <HTMLDivElement>(
      document.getElementById('more-edited-contacts-emails')
    );
    this.more_emails_div.innerHTML = '';
    for (let i = 1; i < InboxComponent.Emails.length; i++) {
      this.more_emails_div.innerHTML += `<input type="text" id="${i}-edited-email">`;
      this.tmpinput = <HTMLInputElement>(
        document.getElementById(`${i}-edited-email`)
      );
      this.tmpinput.value = InboxComponent.Emails[i];
      this.tmpinput.style.width = '90%';
      this.tmpinput.style.marginBottom = '20px';
      this.tmpinput.style.fontSize = '1.1em';
    }
    for(let i = 0; i < InboxComponent.Emails.length; i++){
      (<HTMLInputElement>document.getElementById(`${i}-edited-email`)).value = InboxComponent.Emails[i];
    }
    console.log("past emails:", InboxComponent.Emails.length);
    this.current_contact_edited_number_of_emails = InboxComponent.Emails.length;
    console.log("popup",this.current_contact_edited_number_of_emails)
  }
  setCount(i:number){
    InboxComponent.arrayCounter.push(i);
  }

  addMoreEditedEmails() {
    console.log("addMoreEmails counter",this.current_contact_edited_number_of_emails)
    console.log("addMoreEmails",InboxComponent.Emails.length);
    console.log("addMore AARRAAY counter:" , InboxComponent.arrayCounter);
    //this.current_contact_edited_number_of_emails = InboxComponent.Emails.length;
    this.getAllEditedEmails();
    this.more_emails_div = <HTMLDivElement>(
      document.getElementById('more-edited-contacts-emails')
    );
    this.current_contact_edited_number_of_emails++;
    this.setCount(0);
    this.more_emails_div.innerHTML += `<input type="text" id="${
      InboxComponent.arrayCounter.length - 1
    }-edited-email" placeholder="Enter Email Address">`;
    this.tmpinput = <HTMLInputElement>(
      document.getElementById(
        `${InboxComponent.arrayCounter.length- 1}-edited-email`
      )
    );
    this.tmpinput.style.width = '90%';
    this.tmpinput.style.marginBottom = '20px';
    this.tmpinput.style.fontSize = '1.1em';
    this.display_edited_emails_in_inputField();
  }

  getAllEditedEmails() {
    console.log("the editedddddd :",InboxComponent.arrayCounter.length);
    for (let i = 1; i < InboxComponent.arrayCounter.length; i++) {
      this.current_contact_emails.push(
        (<HTMLInputElement>document.getElementById(`${i}-edited-email`)).value
      );
    }
    console.log(InboxComponent.arrayCounter.length)
  }

  display_edited_emails_in_inputField() {
    for (let i = 1; i < InboxComponent.arrayCounter.length - 1; i++) {
      this.tmpinput = <HTMLInputElement>(
        document.getElementById(`${i}-edited-email`)
      );
      this.tmpinput.value = this.current_contact_emails[i - 1];
    }
    this.current_contact_emails = [];
  }

  async editContact() {
    let newUserName = (<HTMLInputElement>(
      document.getElementById('edited-new-name')
    )).value;
    if (newUserName == '') alert('Please, enter a valid contact name!');
    else {
      let newEmails: string[] = [];
      for (let i = 0; i < InboxComponent.arrayCounter.length; i++) {
        let tmp = (<HTMLInputElement>(
          document.getElementById(`${i}-edited-email`)
        )).value;
        if (tmp == '') continue;
        newEmails.push(tmp);
      }
      if (newEmails.length == 0) alert('Please, enter at least one Email!');
      else {
        let cont = new Contacts(InboxComponent.pre_edited_username,InboxComponent.Emails);
        let contArr : IContact[] = [];
        contArr.push(cont);
        console.log("contARR",contArr);
        this.server
        .removeContact(LogInComponent.currUserName, contArr).subscribe((response) => {
          // this.view =[];
          // this.display_mails([]);
          // alert(response);
          // this.ngOnInit();
        });
        await new Promise(f => setTimeout(f, 100));
        this.server.addContact(LogInComponent.currUserName, newUserName, newEmails).subscribe((response: string) => {
          this.x_edit_clicked();
          this.current_contact_emails = [];
          this.ngOnInit();
        });
      }
    }
  }

  x_edit_clicked() {
    this.lightBlockerField = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.createContactWindow = <HTMLDivElement>(
      document.getElementById('edit-contact-window')
    );
    this.lightBlockerField.style.display = 'none';
    this.createContactWindow.style.display = 'none';
  }

  popupShow(e: any) {
    var emailNumber: number = Number(
      String(e.target.id).substring(0, String(e.target.id).indexOf('-'))
    );

    this.lightBlockerField = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.contactContents_window = <HTMLDivElement>(
      document.getElementById('contact-contents')
    );

    this.contactName_field = <HTMLDivElement>(
      document.getElementById('from-field')
    );
    this.contactEmails_field = <HTMLDivElement>(
      document.getElementById('contact-emails-field')
    );

    var contactName: string =
      ContactsComponent.listOfContacts[emailNumber].name;
    this.contactName_field.innerHTML = contactName;
    this.contactEmails_field.innerHTML = '';

    for (
      let i = 0;
      i < ContactsComponent.listOfContacts[emailNumber].emails.length;
      i++
    ) {
      this.contactEmails_field.innerHTML += `<h3 style = "margin-left:30px;">${ContactsComponent.listOfContacts[emailNumber].emails[i]}</h3>`;
    }

    this.lightBlockerField.style.display = 'block';
    this.contactContents_window.style.display = 'block';
  }

  closePopUp() {
    this.lightBlockerField = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.contactContents_window = <HTMLDivElement>(
      document.getElementById('contact-contents')
    );
    this.lightBlockerField.style.display = 'none';
    this.contactContents_window.style.display = 'none';
  }

  checkTheBox() {
    for (let i = 0; i < ContactsComponent.listOfContacts.length; i++) {
      this.tmpCheck = <HTMLInputElement>document.getElementById(`${i}-check`);
      if (this.tmpCheck.checked)
        this.listOfCheckboxes.push(ContactsComponent.listOfContacts[i]);
    }
  }

  deleteContact() {
    this.checkTheBox();
    if (this.listOfCheckboxes.length > 0) {
      this.server
        .removeContact(LogInComponent.currUserName, this.listOfCheckboxes)
        .subscribe((response) => {
          alert('selected contacts have been deleted');
          // this.x_clicked();
          this.ngOnInit();
        });
    }
  }

  sortContacts(contacts: IContact[]) {
    ContactsComponent.listOfContacts = contacts;
    this.constructView();
    this.display_mails(this.view);
  }
  searchContacts(contacts: IContact[]) {
    ContactsComponent.listOfContacts = contacts;
    this.constructView();
    this.display_mails(this.view);
  }

  select_component_by_color() {
    this.inbox_button = <HTMLButtonElement>(
      document.getElementById('inbox-button')
    );
    this.trash_button = <HTMLButtonElement>(
      document.getElementById('trash-button')
    );
    this.sent_button = <HTMLButtonElement>(
      document.getElementById('sent-button')
    );
    this.draft_button = <HTMLButtonElement>(
      document.getElementById('draft-button')
    );
    this.contacts_button = <HTMLButtonElement>(
      document.getElementById('contacts-button')
    );
    this.folders_button = <HTMLButtonElement>(
      document.getElementById('folders-button')
    );
    this.contacts_button.style.backgroundColor = 'rgba(128, 128, 128, 0.3)';
    this.inbox_button.style.backgroundColor = 'transparent';
    this.sent_button.style.backgroundColor = 'transparent';
    this.draft_button.style.backgroundColor = 'transparent';
    this.trash_button.style.backgroundColor = 'transparent';
    this.folders_button.style.backgroundColor = 'transparent';
  }

  addMoreEmails() {
    this.getAllEmails();
    this.more_emails_div = <HTMLDivElement>(
      document.getElementById('more-contacts-emails')
    );
    this.current_cantact_number_of_emails++;
    this.more_emails_div.innerHTML += `<input type="text" id="${this.current_cantact_number_of_emails}-email" placeholder="Enter Email Address">`;
    this.tmpinput = <HTMLInputElement>(
      document.getElementById(`${this.current_cantact_number_of_emails}-email`)
    );
    this.tmpinput.style.width = '90%';
    this.tmpinput.style.marginBottom = '20px';
    this.tmpinput.style.fontSize = '1.1em';
    this.display_emails_in_inputField();
  }

  getAllEmails() {
    for (let i = 1; i <= this.current_cantact_number_of_emails; i++) {
      this.current_contact_emails.push(
        (<HTMLInputElement>document.getElementById(`${i}-email`)).value
      );
    }
  }

  display_emails_in_inputField() {
    for (let i = 1; i < this.current_cantact_number_of_emails; i++) {
      this.tmpinput = <HTMLInputElement>document.getElementById(`${i}-email`);
      this.tmpinput.value = this.current_contact_emails[i - 1];
    }
    this.current_contact_emails = [];
  }

  displayCraateContactWindow() {
    ContactsComponent.createNewContactClicked = false;
    this.lightBlockerField = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.createContactWindow = <HTMLDivElement>(
      document.getElementById('create-contact-window')
    );
    this.lightBlockerField.style.display = 'block';
    this.createContactWindow.style.display = 'block';
  }

  x_clicked() {
    ContactsComponent.coming_from_contacts = true;
    this.closeCreateContactPopUP();
    this.router.navigate(['/home/inbox']);
    // this.ngOnInit();
  }

  closeCreateContactPopUP() {
    this.lightBlockerField = <HTMLDivElement>(
      document.getElementById('light-blocker')
    );
    this.createContactWindow = <HTMLDivElement>(
      document.getElementById('create-contact-window')
    );
    this.lightBlockerField.style.display = 'none';
    this.createContactWindow.style.display = 'none';
  }

  createContact() {
    this.current_contact_emails = [];
    this.getAllEmails();
    var cantactName = (<HTMLInputElement>(
      document.getElementById('new-contact-name')
    )).value;
    var contact: IContact = new Contacts(
      cantactName,
      this.current_contact_emails
    );
    // send contacts to back
    this.server.addContact(LogInComponent.currUserName, contact.name, contact.emails).subscribe((response: string) => {
      this.current_contact_emails = [];
      this.current_cantact_number_of_emails = 1;
      this.closeCreateContactPopUP();
      // this.ngOnInit();
      ContactsComponent.coming_from_contacts = true;
      this.router.navigate(['/home/inbox']);
    });
  }
  
}
