package Controller;

import Controller.Profile.Components.Contacts.Contact;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;

import java.io.File;
import java.io.FileFilter;

public class Delete {
    private static Delete instance;
    private Delete(){}

    public static Delete getInstance(){
        if(instance == null){
            instance = new Delete();
            System.out.println("Created Delete");
        }

        return instance;
    }


    private static void deleteDataContainer(Container dataContainer) throws Exception{
        boolean deletedFile = dataContainer.getFile().delete();
        if(!deletedFile){
            throw new Exception("can not delete container");
        }
    }

    public void deleteProfile(Profile profile) throws Exception{
        deleteDataContainer(profile.getTrashFolder().getContainer());
        deleteDataContainer(profile.getDraftFolder().getContainer());
        deleteDataContainer(profile.getInboxFolder().getContainer());
        deleteDataContainer(profile.getSentFolder().getContainer());
        deleteDataContainer(profile.getContacts().getContactsContainer());
        deleteDataContainer(profile.getProfileContainer());

    }

    public void deleteEmailDataInbox(Email email, Profile profile) throws Exception{
        if(profile.getInboxFolder().getEmailbyID(email.getEmailID()) == null){
            throw new Exception("email doesn't exist");
        }
        File file = new File(profile.getInboxFolder().getContainer().getContainerPath().concat("/").concat(email.getEmailID()).concat(".json"));
        if(!file.delete()){
            throw new Exception("can not delete email");
        }

    }
    public void deleteEmailDataSent(Email email, Profile profile) throws Exception{
        if(profile.getSentFolder().getEmailbyID(email.getEmailID()) == null){
            throw new Exception("email doesn't exist");
        }
        File file = new File(profile.getSentFolder().getContainer().getContainerPath().concat("/").concat(email.getEmailID()).concat(".json"));
        if(!file.delete()){
            throw new Exception("can not delete email");
        }
    }
    public void deleteEmailDataDraft(Email email, Profile profile) throws Exception{
        if(profile.getDraftFolder().getEmailbyID(email.getEmailID()) == null){
            throw new Exception("email doesn't exist");
        }
        File file = new File(profile.getDraftFolder().getContainer().getContainerPath().concat("/").concat(email.getEmailID()).concat(".json"));
        if(!file.delete()){
            throw new Exception("can not delete email");
        }
    }
    public void deleteEmailDataTrash(Email email, Profile profile) throws Exception{
        if(profile.getTrashFolder().getEmailbyID(email.getEmailID()) == null){
            throw new Exception("No Such Email to Delete");
        }
        File file = new File(profile.getTrashFolder().getContainer().getContainerPath().concat("/").concat(email.getEmailID()).concat(".json"));
        if(!file.delete()){
            throw new Exception("can not delete email");
        }
    }
    public void deleteProfileFolder(Profile profile, String folderName) throws Exception{
        if(profile.getProfileFolderbyName(folderName) == null){
            throw new Exception("folder doesn't exist");
        }
        File file = new File(profile.getProfileFolderbyName(folderName).getContainer().getContainerPath().concat("/"));
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
        for(int i = 0; i < files.length; i++){
            files[i].delete();
        }
        if(!profile.getProfileFolderbyName(folderName).getContainer().getFile().delete()){
            throw new Exception("can not delete the folder");
        }
        profile.removeFolderbyName(folderName);
    }

    public void deleteEmailProfileFolder(Profile profile, String folderName, Email email) throws Exception{
        if(profile.getProfileFolderbyName(folderName).getEmailbyID(email.getEmailID()) == null){
            throw new Exception("email doesn't exist");
        }
        File file = new File(profile.getProfileFolderbyName(folderName).getContainer().getContainerPath().concat("/").concat(email.getEmailID()).concat(".json"));
        if(!file.delete()){
            throw new Exception("can not delete email");
        }
    }

    public void deleteContact(Contact contact, Profile profile) throws Exception{
        if(profile.getContacts().getContact(contact.getName()) == null){
            throw new Exception("contact doesn't exist");
        }
        File file = new File(profile.getContacts().getContactsContainer().getContainerPath().concat("/").concat(contact.getName().concat(".json")));
        if(!file.delete()){
            throw new Exception("can not delete contact");
        }

    }
}
