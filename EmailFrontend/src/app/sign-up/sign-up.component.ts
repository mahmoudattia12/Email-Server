//import { LogInComponent } from 'src/app/log-in/log-in.component';
//import { IEmail } from './../home/home.component';
import { ComposeComponent } from './../home/compose/compose.component';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HomeComponent, IEmail } from '../home/home.component';
import { LogInComponent } from '../log-in/log-in.component';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  constructor(private router: Router, private home: HomeComponent, private http: HttpClient) {
  }


  ngOnInit(): void {

    LogInComponent.reload = false;
  }
  username!: HTMLInputElement;
  password!: HTMLInputElement;
  usererror!: HTMLInputElement;
  passerror!: HTMLInputElement;

  signup() {
    this.username = (<HTMLInputElement>document.getElementById("userName"));
    this.password = (<HTMLInputElement>document.getElementById("password"));
    this.usererror = (<HTMLInputElement>document.getElementById("user-error"));
    this.passerror = (<HTMLInputElement>document.getElementById("password-error"));
    var container: any;
    var loginKey = this.username.value.concat('$').concat(this.password.value);
    console.log(loginKey);
    //this.router.navigate(['/home']);
    if (this.validate()) {
      this.http.post("http://localhost:9080/signup", loginKey, { responseType: "text" })
        .subscribe((response) => {
          container = response;
          console.log(container);
          if (container == "SUCCESS") {
            LogInComponent.currUserName = this.username.value;
            this.router.navigate(['/home']);
          }
          else {
            alert("This username is already used");
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
