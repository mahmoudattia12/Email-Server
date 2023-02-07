import { InboxComponent } from './home/inbox/inbox.component';
import { SentComponent } from './home/sent/sent.component';
import { TrashComponent } from './home/trash/trash.component';
import { DraftComponent } from './home/draft/draft.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ComposeComponent } from './home/compose/compose.component';
import { HomeComponent } from './home/home.component';
import { LogInComponent } from './log-in/log-in.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { FoldersComponent } from './home/folders/folders.component';
import { ContactsComponent } from './home/contacts/contacts.component';
import { InnerFolderComponent } from './home/folders/inner-folder/inner-folder.component';

const routes: Routes = [
  {path: '', component: SignUpComponent},
  {path: 'login', component: LogInComponent},
  {path: 'home', component: HomeComponent, children: [
    {path: 'inbox', component: InboxComponent},
    {path: 'sent', component: SentComponent},
    {path: 'draft', component: DraftComponent},
    {path: 'trash', component: TrashComponent},
    {path: 'compose', component: ComposeComponent},
    {path: 'folders', component: FoldersComponent},
    {path: 'contacts', component: ContactsComponent},
    {path: 'inner-folder',component:InnerFolderComponent}
  ]},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
