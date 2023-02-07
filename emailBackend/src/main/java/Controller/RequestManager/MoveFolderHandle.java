package Controller.RequestManager;

import Controller.Creator;
import Controller.Database;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;

import java.util.UUID;

public class MoveFolderHandle implements IHandle {
    private final String request = "moveFolder";
    private IHandle successor = MoveTrashHandle.getInstance();
    private static MoveFolderHandle instance = null;
    public static MoveFolderHandle getInstance(){
        if(instance == null){
            instance = new MoveFolderHandle();
        }
        return instance;
    }
    @Override
    public void handle(String request, Email email, String folderName) throws Exception {
        if(request == this.request) {
            Database database = Database.getInstance();
            Profile ownerProfile = database.getProfileByName("", email.getOwner());
            if (ownerProfile == null) {
                throw new Exception("User Name Not Found");
            }
            String emailID = UUID.randomUUID().toString();
            Creator creator = Creator.getInstance();
            String emailType = email.getEmailType();

            if (emailType.equals("Inbox") || emailType.equals("Sent") || emailType.equals("Draft")) {
                ownerProfile.getProfileFolderbyName(folderName).addEmail(creator.createEmailDataProfileFolder(email, ownerProfile, folderName, emailID));
            }
        }
        else {
            if(this.successor == null){
                throw new Exception("no valid handler");
            }
            this.successor.handle(request, email, folderName);
        }
    }
}
