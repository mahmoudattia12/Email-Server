package Controller.Profile.Components.Email;

import java.util.ArrayList;

public class Email{
    private String senderName, owner, subject, body, timeSent, priority, emailID, emailType;

    private  ArrayList<String> receiverNames;
    private ArrayList<Attachment> attachments;

    public Email() {}
    public Email(String senderName, ArrayList<String> receiverNames, String owner, String subject, String body,
                 String timeSent, String priority, String emailID, String emailType, ArrayList<Attachment> attachments){
        this.senderName = senderName;
        this.receiverNames = receiverNames;
        this.owner = owner;
        this.subject = subject;
        this.body = body;
        this.timeSent = timeSent;
        this.priority = priority;
        this.emailID = emailID;
        this.emailType = emailType;
        this.attachments = attachments;
    }

    public String getSenderName() {
        return this.senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public ArrayList<String> getReceiverNames() {
        return this.receiverNames;
    }

    public void setReceiverNames(ArrayList<String> receiverNames) {
        this.receiverNames = receiverNames;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTimeSent() {
        return this.timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getEmailID() {
        return this.emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getEmailType() {
        return this.emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public ArrayList<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void addAttachment(Attachment attach){
        this.attachments.add(attach);
    }
    public void deleteAttachment(Attachment attach){
        this.attachments.remove(attach);
    }
}
