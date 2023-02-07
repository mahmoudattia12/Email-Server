package Controller.RequestManager;

import Controller.Creator;
import Controller.Database;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;

import java.util.UUID;

public class MoveDraftHandle implements IHandle {
    private final String request = "moveDraft";
    private IHandle successor = MoveFolderHandle.getInstance();
    private static MoveDraftHandle instance = null;
    public static MoveDraftHandle getInstance(){
        if(instance == null){
            instance = new MoveDraftHandle();
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
            String DraftId = UUID.randomUUID().toString();
            Creator.getInstance().createEmailDataDraft(email, ownerProfile, DraftId);

            /////////////////////////////
            ///remove it from sent
            ////////////////////////////
        }
        else {
            if(this.successor == null){
                throw new Exception("no valid handler");
            }
            this.successor.handle(request, email, folderName);
        }
    }
}
