package Controller.RequestManager;

import Controller.Creator;
import Controller.Database;
import Controller.Delete;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;

public class MoveTrashHandle implements IHandle{
    private final String request = "moveTrash";
    private IHandle successor = PermanentDeleteHandle.getInstance();

    private static MoveTrashHandle instance = null;
    public static MoveTrashHandle getInstance(){
        if(instance == null) {
            instance = new MoveTrashHandle();
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

            if (email.getEmailType().equals("Inbox") && folderName == "") {
                Creator.getInstance().createEmailDataTrash(email, ownerProfile, email.getEmailID());
                Delete.getInstance().deleteEmailDataInbox(email, ownerProfile);
                ownerProfile.getInboxFolder().removeEmailbyID(email.getEmailID());
                ownerProfile.getTrashFolder().addEmail(email);
            }

            if (email.getEmailType().equals("Sent") && folderName == "") {
                Creator.getInstance().createEmailDataTrash(email, ownerProfile, email.getEmailID());
                Delete.getInstance().deleteEmailDataSent(email, ownerProfile);
                ownerProfile.getSentFolder().removeEmailbyID(email.getEmailID());
                ownerProfile.getTrashFolder().addEmail(email);

            }

            if (email.getEmailType().equals("Draft") && folderName == "") {
                Creator.getInstance().createEmailDataTrash(email, ownerProfile, email.getEmailID());
                Delete.getInstance().deleteEmailDataDraft(email, ownerProfile);
                ownerProfile.getDraftFolder().removeEmailbyID(email.getEmailID());
                ownerProfile.getTrashFolder().addEmail(email);
            }

            if (folderName != "") {
                Delete.getInstance().deleteEmailProfileFolder(ownerProfile, folderName, email);
                ownerProfile.getProfileFolderbyName(folderName).removeEmailbyID(email.getEmailID());
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
