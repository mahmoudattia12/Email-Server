package Controller.EmailFilter;

import Controller.EmailFilter.IEmailCriteria;
import Controller.Profile.Components.Email.Email;

import java.util.ArrayList;

public class SearchFiltered implements IEmailCriteria {
    private String content;

    public SearchFiltered(String target) {
        this.content = target;
    }

    public String getTarget() {
        return this.content;
    }

    public void setTarget(String content) {
        this.content = content;
    }

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emails) {
        ArrayList<Email> filteredEmails = new ArrayList<>();
        String searchedemail = "";
        for (Email E : emails) {
            searchedemail = (E.getBody().toLowerCase()).
                    concat(E.getSenderName().toLowerCase().
                            concat(E.getPriority().toLowerCase().
                                    concat(E.getSubject().toLowerCase().
                                            concat(E.getTimeSent().toLowerCase().
                                                    concat(E.getAttachments().toString().toLowerCase().
                                                            concat(E.getReceiverNames().get(0).toLowerCase()))))));//Get Reciever name && Get Attachments you may change them.
            if (searchedemail.contains(this.content.toLowerCase())) {
                filteredEmails.add(E);
            }

        }
        return filteredEmails;
    }
}

