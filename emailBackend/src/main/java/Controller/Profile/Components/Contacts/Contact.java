package Controller.Profile.Components.Contacts;

import java.util.ArrayList;

public class Contact {
    private String name;
    private ArrayList<String> emails;
    public Contact(){}

    public Contact(String name, ArrayList<String> emails){
        this.name = name;
        this.emails = emails;
    }

    public String getName() {
        System.out.println("inside getName(): "+name);
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEmails() {
        return this.emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public void addEmail( String email){
        this.emails.add(email);
    }
}
