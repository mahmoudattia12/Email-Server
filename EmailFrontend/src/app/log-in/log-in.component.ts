import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ComposeComponent } from '../home/compose/compose.component';
import { HomeComponent } from '../home/home.component';

@Component({
  selector: 'app-log-in',
  templateUrl: './log-in.component.html',
  styleUrls: ['./log-in.component.css']
})
export class LogInComponent implements OnInit {

  constructor(private router: Router, private home: HomeComponent, private http: HttpClient) {
    //ComposeComponent.sentEmail.owner = "";
  }

  ngOnInit(): void {
  }
  public static currUserName: string ;
  public static reload: boolean = false;
  username!: HTMLInputElement;
  password!: HTMLInputElement;
  usererror!: HTMLInputElement;
  passerror!: HTMLInputElement;

  login() {
    this.username = (<HTMLInputElement>document.getElementById("username"));
    this.password = (<HTMLInputElement>document.getElementById("password"));
    this.usererror = (<HTMLInputElement>document.getElementById("username-error"));
    this.passerror = (<HTMLInputElement>document.getElementById("password-error"));
    var container: string;
    var loginKey = this.username.value.concat('$').concat(this.password.value);
    console.log(loginKey);
    if (this.validate()) {
        this.http.get('http://localhost:9080/login?loginKey='+
          loginKey,
          {responseType: "text"}).subscribe((response) => {
              container = response;
              console.log(container);
              if(container == "") {
                alert("No such profile");
              }
              else{
                LogInComponent.currUserName= this.username.value;
                this.router.navigate(['/home']);
              }
          })
    }

  }
  validate() {
    if (this.username.value.length < 4) {
      this.username.style.border = '1px solid red';
      this.usererror.style.display = 'block';
      this.username.focus();
      return false;
    }
    else {
      this.username.style.border = '1px solid silver';
      this.usererror.style.display = 'none';
    }
    if (this.password.value.length < 8) {
      this.password.style.border = '1px solid red';
      this.passerror.style.display = 'block';
      this.password.focus();
      return false;
    }
    else {
      this.password.style.border = '1px solid silver';
      this.passerror.style.display = 'none';
    }
    return true;
  }
}
