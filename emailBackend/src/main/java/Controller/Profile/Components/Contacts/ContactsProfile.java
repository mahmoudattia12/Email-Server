package Controller.Profile.Components.Contacts;

import Controller.Container;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;

public class ContactsProfile {
    private Container contactsContainer;
    private ArrayList<Contact> contacts;

    public ContactsProfile(Container contactsContainer) throws Exception {
        this.contactsContainer = contactsContainer;
        this.contacts = new ArrayList<>();
        this.readContacts();
    }

    private void readContacts() throws Exception {
        File file = new File(this.contactsContainer.getContainerPath().concat("/"));
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        if(file == null || files == null){
            throw new Exception("oops Directory not found");
        }
        for(int i = 0; i < files.length; i++){
            ObjectMapper map = new ObjectMapper();
            Contact con = map.readValue(files[i], Contact.class);
            this.addContact(con);
        }
    }

    public Container getContactsContainer() {
        return this.contactsContainer;
    }

    public ArrayList<Contact> getContacts() {
        return this.contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }
    public void addContact (Contact con){
        this.contacts.add(con);
    }
    public void deleteContact (String conName){
        this.contacts.remove(this.getContact(conName));
    }
    public Contact getContact(String contactName){
        for(int i = 0; i < this.contacts.size(); i++){
            Contact tmp = this.contacts.get(i);
            if(tmp.getName().equals(contactName)){
                return tmp;
            }
        }
        return null;
    }

}
