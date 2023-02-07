import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { LogInComponent } from './log-in/log-in.component';
import { HomeComponent } from './home/home.component';
import { ComposeComponent } from './home/compose/compose.component';
import { HttpClientModule } from '@angular/common/http';
import { SentComponent } from './home/sent/sent.component';
import { DraftComponent } from './home/draft/draft.component';
import { TrashComponent } from './home/trash/trash.component';
import { InboxComponent } from './home/inbox/inbox.component';
import { FoldersComponent } from './home/folders/folders.component';
import { ContactsComponent } from './home/contacts/contacts.component';
import { InnerFolderComponent } from './home/folders/inner-folder/inner-folder.component';

@NgModule({
  declarations: [
    AppComponent,
    SignUpComponent,
    LogInComponent,
    HomeComponent,
    ComposeComponent,
    SentComponent,
    DraftComponent,
    TrashComponent,
    InboxComponent,
    FoldersComponent,
    ContactsComponent,
    InnerFolderComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [
    SignUpComponent,
    LogInComponent,
    HomeComponent,
    ComposeComponent,
    SentComponent,
    DraftComponent,
    TrashComponent,
    InboxComponent,
    FoldersComponent,
    ContactsComponent,
    InnerFolderComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
