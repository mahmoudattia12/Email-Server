package Controller.Profile;

import Controller.Container;
import Controller.Profile.Components.*;
import Controller.Profile.Components.Contacts.ContactsProfile;

import java.util.ArrayList;

public class Profile {
    private String username;
    private String emailAddress;
    private String password;
    private String loginKey;
    private Container profileContainer;
    private Inbox inboxFolder;
    private Sent sentFolder;
    private Draft draftFolder;
    private Trash trashFolder;
    private ArrayList<UserFolder> userfolders;
    private ContactsProfile contacts;

    public Profile(){
        this.userfolders = new ArrayList<>();
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginKey() {
        return this.loginKey;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;
    }

    public Container getProfileContainer() {
        return this.profileContainer;
    }

    public void setProfileContainer(Container profileContainer) {
        this.profileContainer = profileContainer;
    }

    public Inbox getInboxFolder() {
        return this.inboxFolder;
    }

    public void setInboxFolder(Inbox inboxFolder) {
        this.inboxFolder = inboxFolder;
    }

    public Sent getSentFolder() {
        return this.sentFolder;
    }

    public void setSentFolder(Sent sentFolder) {
        this.sentFolder = sentFolder;
    }

    public Draft getDraftFolder() {
        return this.draftFolder;
    }

    public void setDraftFolder(Draft draftFolder) {
        this.draftFolder = draftFolder;
    }

    public Trash getTrashFolder() {
        return this.trashFolder;
    }

    public void setTrashFolder(Trash trashFolder) {
        this.trashFolder = trashFolder;
    }

    public ArrayList<UserFolder> getUserfolders() {
        return this.userfolders;
    }

    public void setUserfolders(ArrayList<UserFolder> userfolders) {
        this.userfolders = userfolders;
    }

    public ContactsProfile getContacts() {
        return this.contacts;
    }

    public void setContacts(ContactsProfile contacts) {
        this.contacts = contacts;
    }

    public UserFolder getProfileFolderbyName(String name) {
        UserFolder profileFolder = null;
        for(int i = 0; i < this.userfolders.size(); i++){
            if(name.equals(this.userfolders.get(i).getContainer().getContainerName())){
                profileFolder = this.userfolders.get(i);
            }
        }
        return profileFolder;
    }
    public void removeFolderbyName(String name) {
        UserFolder profileFolder = null;
        for(int i = 0; i < this.userfolders.size(); i++){
            if(name.equals(this.userfolders.get(i).getContainer().getContainerName())){
                removeFolder(this.userfolders.get(i));
            }
        }

    }

    public void setFolders(ArrayList<UserFolder> folders) {
        this.userfolders = folders;
    }

    public ArrayList<UserFolder> getFolders(){
        return this.userfolders;
    }

    public void removeFolder(UserFolder profileFolder) {
        this.userfolders.remove(profileFolder);
    }

    public void addFolder(UserFolder profileFolder) {
        this.userfolders.add(profileFolder);
    }
}
