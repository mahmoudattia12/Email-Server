import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LogInComponent } from 'src/app/log-in/log-in.component';
import { HomeComponent } from '../home.component';
import { FolderService } from './folders.service';

@Component({
  selector: 'app-folders',
  templateUrl: './folders.component.html',
  styleUrls: ['./folders.component.css']
})
export class FoldersComponent  implements OnInit{
  inbox_button!:HTMLButtonElement;
  sent_button!:HTMLButtonElement;
  draft_button!:HTMLButtonElement;
  contacts_button!:HTMLButtonElement;
  folders_button!:HTMLButtonElement;
  trash_button!:HTMLButtonElement;

  public static createNewFolderClicked:boolean = false;
  public static listOfFolders:string[] = [];
  public static no_of_folders:number = 0;
  public static new_to_original_name = new Map<string,string>();
  public static folderSelected:string;
  lightBlockerField!:HTMLDivElement;
  createFolderWindow!:HTMLDivElement;
  newFolderNameField!:HTMLInputElement;
  folderDropDownList!:HTMLSelectElement;
  renameFolderWindow!:HTMLDivElement;
  horizontal!:HTMLHRElement;
  tmpDiv!:HTMLDivElement;


  constructor(private router: Router, private server: FolderService) {
    LogInComponent.reload = false;
    HomeComponent.currPage = "FoldersPage"
  }

  ngOnInit(): void {
    (<HTMLDivElement>document.getElementById("emails-options")).style.display = "none";
    (<HTMLHRElement>document.getElementById("horizontal-line")).style.display = "none";
    (<HTMLDivElement>document.getElementById("search-field")).style.display = "none";

    this.select_component_by_color();

    this.uploadFoldersToList();
    if(FoldersComponent.createNewFolderClicked)this.displayCraateFolderWindow();
    // this.tmpDiv = <HTMLDivElement>document.getElementById("view-folder-options");
    // this.tmpDiv.style.display = "none"
  }


  displayCraateFolderWindow(){
    this.lightBlockerField = <HTMLDivElement>document.getElementById("light-blocker");
    this.createFolderWindow = <HTMLDivElement>document.getElementById("create-folder-window");
    this.newFolderNameField = <HTMLInputElement>document.getElementById("new-folder-name");
    this.newFolderNameField.value = "";
    this.lightBlockerField.style.display = "block";
    this.createFolderWindow.style.display = "block";
  }


  createFolder(){
    this.newFolderNameField = <HTMLInputElement>document.getElementById("new-folder-name");
    if(this.newFolderNameField.value == "")alert("plese, Enter a name!");
    else{
      if(this.checkFolderExistence(this.newFolderNameField.value))alert("Folder already exists");
      else{
        FoldersComponent.no_of_folders++;
        FoldersComponent.listOfFolders.push(this.newFolderNameField.value);
        FoldersComponent.new_to_original_name.set(this.newFolderNameField.value,this.newFolderNameField.value);
        this.folderDropDownList = <HTMLSelectElement>document.getElementById("folder_select");
        this.folderDropDownList.innerHTML += `<option value=${this.newFolderNameField.value}>${this.newFolderNameField.value}</option>`
        this.server.addUserFolder(LogInComponent.currUserName,this.newFolderNameField.value)
        .subscribe((response)=>{
          console.log(response);
          alert(`${this.newFolderNameField.value} folder has been created`)
        });

        this.closeCreateFolderPopUP();
      }
    }
  }


  closeCreateFolderPopUP(){
    FoldersComponent.createNewFolderClicked = false;
    this.lightBlockerField = <HTMLDivElement>document.getElementById("light-blocker");
    this.createFolderWindow = <HTMLDivElement>document.getElementById("create-folder-window");
    this.lightBlockerField.style.display = "none";
    this.createFolderWindow.style.display = "none";
  }

  selectClicked(){

    this.tmpDiv = <HTMLDivElement>document.getElementById("view-folder-options");
    if(FoldersComponent.listOfFolders.length  != 0){
      FoldersComponent.folderSelected = (<HTMLSelectElement>document.getElementById("folder_select")).value;
      console.log(FoldersComponent.folderSelected);
      this.tmpDiv.style.display = "block";
    }
  }


  selectOperationClicked(){
    var folderOperation = (<HTMLSelectElement>document.getElementById("folder_options")).value;
    if(folderOperation == "deleteFolder"){
        FoldersComponent.listOfFolders.splice(FoldersComponent.listOfFolders.indexOf(FoldersComponent.folderSelected),1);
        FoldersComponent.new_to_original_name.delete(FoldersComponent.folderSelected);

        this.server.removeUserFolder(LogInComponent.currUserName,String(FoldersComponent.new_to_original_name.get(FoldersComponent.folderSelected)))
        .subscribe((response)=>{
          console.log(response);
          alert(`${FoldersComponent.folderSelected} folder has been deleted`);
        })

        FoldersComponent.folderSelected = (<HTMLSelectElement>document.getElementById("folder_select")).value;
        this.uploadFoldersToList();

    }

    else if(folderOperation == "renameFolder"){
      this.renameFolderWindow = <HTMLDivElement>document.getElementById("rename-folder-window");
      this.lightBlockerField =  <HTMLDivElement>document.getElementById("light-blocker");
      this.renameFolderWindow.style.display = "block";
      this.lightBlockerField.style.display = "block";
    }
    else{
      FoldersComponent.folderSelected = String(FoldersComponent.new_to_original_name.get(FoldersComponent.folderSelected));

      this.tmpDiv = <HTMLDivElement>document.getElementById("display-folders-and-options");
      console.log("folder selected is:",FoldersComponent.folderSelected);
      this.router.navigate(['/home/inner-folder']);
      // this.tmpDiv.style.display = "none"
    }
  }


  renameFolder(){
    var newName:string = (<HTMLInputElement>document.getElementById("folder-new-name")).value;
    console.log(newName);
    if(newName == "")alert("Please, enter a name!");
    else{
      if(this.checkFolderExistence(newName))alert("Folder already exists");
      else{
        for(let i = 0 ; i< FoldersComponent.listOfFolders.length ; i++){
          if(FoldersComponent.listOfFolders[i] == FoldersComponent.folderSelected){
            FoldersComponent.listOfFolders[i] = newName;
            var tmp = FoldersComponent.new_to_original_name.get(FoldersComponent.folderSelected);
            FoldersComponent.new_to_original_name.delete(FoldersComponent.folderSelected);
            FoldersComponent.new_to_original_name.set(newName,String(tmp));
            FoldersComponent.folderSelected = newName;
            this.uploadFoldersToList();
            this.closeRenameWindow();
            console.log(FoldersComponent.new_to_original_name);
            return;
          }
        }
      }
    }
  }


  closeRenameWindow(){
    this.renameFolderWindow = <HTMLDivElement>document.getElementById("rename-folder-window");
    this.lightBlockerField =  <HTMLDivElement>document.getElementById("light-blocker");
    this.renameFolderWindow.style.display = "none";
    this.lightBlockerField.style.display = "none";
  }

  checkFolderExistence(name:string){
    for(let i = 0 ; i<FoldersComponent.listOfFolders.length ; i++){
      if(FoldersComponent.listOfFolders[i] == name){
          return true;
      }
    }
    return false;
  }

  uploadFoldersToList(){
    this.folderDropDownList = <HTMLSelectElement>document.getElementById("folder_select");
    this.folderDropDownList.innerHTML = "";
    for(let i = 0 ; i<FoldersComponent.listOfFolders.length;i++){
      this.folderDropDownList.innerHTML += `<option value=${FoldersComponent.listOfFolders[i]} id = ${FoldersComponent.listOfFolders[i]}>${FoldersComponent.listOfFolders[i]}</option>`
    }
  }


  select_component_by_color(){
    this.inbox_button = <HTMLButtonElement>document.getElementById("inbox-button");
    this.trash_button = <HTMLButtonElement>document.getElementById("trash-button");
    this.sent_button = <HTMLButtonElement>document.getElementById("sent-button");
    this.draft_button = <HTMLButtonElement>document.getElementById("draft-button");
    this.contacts_button = <HTMLButtonElement>document.getElementById("contacts-button");
    this.folders_button = <HTMLButtonElement>document.getElementById("folders-button");
    this.folders_button.style.backgroundColor = "rgba(128, 128, 128, 0.3)";
    this.inbox_button.style.backgroundColor = "transparent";
    this.sent_button.style.backgroundColor = "transparent";
    this.draft_button.style.backgroundColor = "transparent";
    this.contacts_button.style.backgroundColor = "transparent";
    this.trash_button.style.backgroundColor = "transparent";
  }


}

