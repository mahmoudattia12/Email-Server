package Controller.RequestManager;

import Controller.Creator;
import Controller.Database;
import Controller.Delete;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;

import java.util.ArrayList;
import java.util.UUID;

public class SendEmailHandle implements IHandle {
    private final String request = "sendEmail";
    private IHandle successor = MoveDraftHandle.getInstance();
    private static SendEmailHandle instance = null;
    public static SendEmailHandle getInstance(){
        if(instance == null){
            instance = new SendEmailHandle();
        }
        return instance;
    }

    public void handle(String request, Email email, String folderName) throws Exception {
        if (request == this.request) {
            Database database = Database.getInstance();
            if (database.getProfileByName("", email.getSenderName()) == null) {

                throw new Exception("User Name Not Found");
            }

            Creator creator = Creator.getInstance();

            Profile senderProfile = database.getProfileByName("", email.getSenderName());

            String senderID = UUID.randomUUID().toString();

            senderProfile.getSentFolder().addEmail(creator.createEmailDataSent(email, senderProfile, senderID));

            ArrayList<String> receivers = email.getReceiverNames();

            for (int i = 0; i < receivers.size(); i++) {

                String receiver = receivers.get(i);

                Profile receiverProfile = database.getProfileByName("", receiver);

                if (receiverProfile != null) {

                    String receiverID = UUID.randomUUID().toString();

                    receiverProfile.getInboxFolder().addEmail(creator.createEmailDataInbox(email, receiverProfile, receiverID, i));

                }
            }
            if (email.getEmailType() != null) {

                if (email.getEmailType().equals("Draft")) {

                    Delete.getInstance().deleteEmailDataDraft(email, senderProfile);

                    senderProfile.getDraftFolder().removeEmailbyID(email.getEmailID());
                }
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
