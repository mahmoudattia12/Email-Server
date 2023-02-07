package Controller.RequestManager;

import Controller.Database;
import Controller.Delete;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;

public class PermanentDeleteHandle implements IHandle {
    private final String request = "permanentDelete";
    private IHandle successor = RestoreHandle.getInstance();
    private static PermanentDeleteHandle instance = null;
    public static PermanentDeleteHandle getInstance(){
        if(instance == null){
            instance = new PermanentDeleteHandle();
        }
        return instance;
    }
    @Override
    public void handle(String request, Email email, String folderName) throws Exception {
        if(request == this.request) {
            Database database = Database.getInstance();
            Profile ownerProfile = database.getProfileByName("", email.getOwner());
//            if (email.getEmailType().equals("Draft")) {
//                Delete.getInstance().deleteEmailDataDraft(email, ownerProfile);
//                ownerProfile.getDraftFolder().removeEmailbyID(email.getEmailID());

                Delete.getInstance().deleteEmailDataTrash(email, ownerProfile);
                ownerProfile.getTrashFolder().removeEmailbyID(email.getEmailID());

        }
        else {
            if (this.successor == null) {
                throw new Exception("no valid handler");
            }
            this.successor.handle(request, email, folderName);
        }
    }
}
