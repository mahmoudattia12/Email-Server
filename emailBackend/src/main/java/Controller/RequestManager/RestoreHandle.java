package Controller.RequestManager;

import Controller.Creator;
import Controller.Database;
import Controller.Delete;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;

public class RestoreHandle implements IHandle {
    private final String request = "restore";
    private IHandle successor = null;
    private static RestoreHandle instance = null;
    public static RestoreHandle getInstance(){
        if(instance == null){
            instance = new RestoreHandle();
        }
        return instance;
    }
    @Override
    public void handle(String request, Email email, String folderName) throws Exception {
        if(request == this.request) {
            Database database = Database.getInstance();
            Profile ownerProfile = database.getProfileByName("", email.getOwner());

            Delete.getInstance().deleteEmailDataTrash(email, ownerProfile);
            ownerProfile.getTrashFolder().removeEmailbyID(email.getEmailID());

            Creator create = Creator.getInstance();

            if (email.getEmailType().equals("Inbox")) {
                create.createEmailDataInbox(email, ownerProfile, email.getEmailID(), 0);
                ownerProfile.getInboxFolder().addEmail(email);
            } else if (email.getEmailType().equals("Sent")) {
                create.createEmailDataSent(email, ownerProfile, email.getEmailID());
                ownerProfile.getSentFolder().addEmail(email);
            }
        }
        else{
            if(this.successor == null){
                throw new Exception("no valid handler");
            }
            this.successor.handle(request, email, folderName);
        }
    }
}
