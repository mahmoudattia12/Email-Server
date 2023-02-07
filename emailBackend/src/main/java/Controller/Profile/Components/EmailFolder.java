package Controller.Profile.Components;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

import com.fasterxml.jackson.databind.ObjectMapper;

import Controller.Container;
import Controller.Profile.Components.Email.Email;

public class EmailFolder {
    private Container container;

    private ArrayList<Email> emails;

    private PriorityQueue<Email> Prioritizedemails;


    private static ArrayList<String> priorities = new ArrayList<>(
            Arrays.asList("So Important", "Important", "Default", "Low")
    );

    public EmailFolder(Container container)throws Exception {
        this.container = container;
        this.emails = new ArrayList<Email>();
        this.Prioritizedemails = new PriorityQueue<Email>((p1, p2) -> {
            if(priorities.indexOf(p1.getPriority()) < priorities.indexOf(p2.getPriority())){
                return -1;
            }
            else{
                return 1;
            }
        });
        this.setEmails();
    }

    public Container getContainer() {
        return this.container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public ArrayList<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(ArrayList<Email> emails) {
        this.emails = emails;
    }

    public PriorityQueue<Email> getPrioritizedemails() {
        return this.Prioritizedemails;
    }

    public void setPrioritizedemails(PriorityQueue<Email> prioritizedemails) {
        this.Prioritizedemails = prioritizedemails;
    }
    private void setEmails() throws Exception {
        File file = new File(this.container.getContainerPath().concat("/"));
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        if(file == null || files == null){
            throw new Exception("NO SUCH DIRECOTRY");
        }
        for(int i = 0; i < files.length; i++){
            ObjectMapper map = new ObjectMapper();
            Email email = map.readValue(files[i], Email.class);
            this.addEmail(email);
        }
    }

    public void addEmail(Email email) {
        this.emails.add(email);
        this.Prioritizedemails.add(email);
    }

    public void removeEmail(Email email) {
        this.emails.remove(email);
        this.Prioritizedemails.remove(email);
    }

    public void removeEmailbyID(String ID) {
        for(int i = 0; i < this.emails.size(); i++){
            if(ID.equals(this.emails.get(i).getEmailID())){
                this.Prioritizedemails.remove(this.emails.get(i));
                this.emails.remove(i);
            }
        }
    }

    public Email getEmailbySubject(String subject){
        Email email = null;
        for(int i = 0; i < this.emails.size(); i++){
            if(subject.equals(this.emails.get(i).getSubject())){
                email = this.emails.get(i);
            }
        }
        return email;
    }

    public Email getEmailbyBody(String body) {
        Email email = null;
        for(int i = 0; i < this.emails.size(); i++){
            if(body.equals(this.emails.get(i).getBody())){
                email = this.emails.get(i);
            }
        }
        return email;
    }

    public Email getEmailbySenderUsername(String username) {
        Email email = null;
        for(int i = 0; i < this.emails.size(); i++){
            if(username.equals(this.emails.get(i).getSenderName())){
                email = this.emails.get(i);
            }
        }
        return email;
    }

    public Email getEmailbyreceiverUsername(String username) {
        Email email = null;
        for(int i = 0; i < this.emails.size(); i++){
            if(username.equals(this.emails.get(i).getReceiverNames().get(0))){
                email = this.emails.get(i);
            }
        }
        return email;
    }

    public Email getEmailbyID(String ID) {
        Email email = null;
        for(int i = 0; i < this.emails.size(); i++){
            if(ID.equals(this.emails.get(i).getEmailID())){
                email = this.emails.get(i);
            }
        }
        return email;
    }
}