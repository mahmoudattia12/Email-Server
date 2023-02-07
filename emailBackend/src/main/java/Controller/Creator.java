package Controller;

import Controller.Profile.Builder.ProfileBuilder;
import Controller.Profile.Builder.ProfileDirector;
import Controller.Profile.Builder.ProfilePlanI;
import Controller.Profile.Components.*;
import Controller.Profile.Components.Contacts.Contact;
import Controller.Profile.Components.Contacts.ContactsProfile;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Profile;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;



public class Creator {
    private static Creator instance;

    private Creator() {}
    public static Creator getInstance(){
        if(instance == null){
            instance = new Creator();
        }
        return instance;
    }

    private static Container createDataContainer(String dataContainerPath, String dataContainerName) throws Exception{
        File file = new File(dataContainerPath);
        boolean createdFile = file.mkdir();
        if(createdFile){
            return new Container(dataContainerPath, dataContainerName, file);
        }
        else{
            throw new Exception("can not create container");
        }
    }


    public Profile createProfile(String dataBasePath, String encryption) throws Exception{
        ProfilePlanI profileBuilder = new ProfileBuilder();
        ProfileDirector profileDirector = new ProfileDirector(profileBuilder);

        profileDirector.buildProfileData(encryption);
        profileDirector.buildDataContainer(createDataContainer(dataBasePath.concat(encryption), encryption));

        profileDirector.buildInbox(createDataContainer(dataBasePath.concat(encryption).concat("/Inbox"), "Inbox"));
        profileDirector.buildSent(createDataContainer(dataBasePath.concat(encryption).concat("/Sent"), "Sent"));
        profileDirector.buildDraft(createDataContainer(dataBasePath.concat(encryption).concat("/Draft"), "Draft"));
        profileDirector.buildTrash(createDataContainer(dataBasePath.concat(encryption).concat("/Trash"), "Trash"));
        profileDirector.buildContacts(createDataContainer(dataBasePath.concat(encryption).concat("/Contacts"), "Contacts"));
        return profileDirector.getTheFinalProfile();
    }

    public Profile setProfile(String dataBasePath, String encryption) throws Exception{
        Profile profile = new Profile();

        File file = new File(dataBasePath.concat(encryption).concat("/"));
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        profile.setProfileContainer(new Container(dataBasePath.concat(encryption), encryption, new File(dataBasePath.concat(encryption))));
        profile.setLoginKey(encryption);
        profile.setUsername(encryption.substring(0, encryption.indexOf("$")));
        profile.setPassword(encryption.substring(encryption.indexOf("$") + 1));
        for(int i = 0 ; i < files.length; i++){
            System.out.println(files[i].getName());
            if(files[i].getName().equals("Inbox")){
                profile.setInboxFolder(new Inbox(new Container(dataBasePath.concat(encryption).concat("/Inbox"), "Inbox", new File(dataBasePath.concat(encryption).concat("/Inbox")))));
            }
            else if(files[i].getName().equals("Sent")){
                profile.setSentFolder(new Sent(new Container(dataBasePath.concat(encryption).concat("/Sent"), "Sent", new File(dataBasePath.concat(encryption).concat("/Sent")))));
            }
            else if(files[i].getName().equals("Draft")){
                profile.setDraftFolder(new Draft(new Container(dataBasePath.concat(encryption).concat("/Draft"), "Draft", new File(dataBasePath.concat(encryption).concat("/Draft")))));
            }
            else if(files[i].getName().equals("Trash")){
                profile.setTrashFolder(new Trash(new Container(dataBasePath.concat(encryption).concat("/Trash"), "Trash", new File(dataBasePath.concat(encryption).concat("/Trash")))));
            }
            else if(files[i].getName().equals("Contacts")){
                profile.setContacts(new ContactsProfile(new Container(dataBasePath.concat(encryption).concat("/Contacts"), "Contacts", new File(dataBasePath.concat(encryption).concat("/Contacts")))));
            }
            else{
                profile.addFolder(new UserFolder(new Container(dataBasePath.concat(encryption).concat("/").concat(files[i].getName()), files[i].getName(), new File(dataBasePath.concat(encryption).concat("/").concat(files[i].getName()))), files[i].getName()));
            }
        }
        return profile;
    }

    public void createProfileFolder(String name, Profile profile) throws Exception{
        System.out.println(profile.getProfileContainer());
        UserFolder profileFolder = new UserFolder(createDataContainer(profile.getProfileContainer().getContainerPath().concat("/").concat(name), name), name);
        profile.addFolder(profileFolder);
    }

    public void createDataFile(Profile profile) throws Exception{
        File file = new File(profile.getProfileContainer().getContainerPath().concat("/PROFILE.json"));
        if(!file.createNewFile()){
            throw new Exception("can not create profile data");
        }
        ObjectMapper map = new ObjectMapper();
        map.writeValue(file, profile);
    }

    public Email createEmailDataInbox(Email email, Profile profile, String ID, int receiverIndex) throws Exception{
        Email inboxEmail = new Email(email.getSenderName(), new ArrayList<String>(Collections.singleton(email.getReceiverNames().get(receiverIndex))), email.getOwner(), email.getSubject(), email.getBody(), email.getTimeSent(), email.getPriority(), email.getEmailID(), "Inbox", email.getAttachments());
        File file = new File(profile.getInboxFolder().getContainer().getContainerPath().concat("/").concat(ID).concat(".json"));
        if(!file.createNewFile()){
            throw new Exception("can not create inbox");
        }
        inboxEmail.setEmailID(ID);
        if(receiverIndex != -1){
            inboxEmail.setOwner(email.getReceiverNames().get(receiverIndex));
        }
        inboxEmail.setEmailType("Inbox");
        ObjectMapper map = new ObjectMapper();
        map.writeValue(file, inboxEmail);
        return inboxEmail;
    }


    public Email createEmailDataSent(Email email, Profile profile, String ID) throws Exception{
        Email sentEmail = new Email(email.getSenderName(), email.getReceiverNames(), email.getOwner(), email.getSubject(), email.getBody(), email.getTimeSent(), email.getPriority(), email.getEmailID(), "Sent", email.getAttachments());
        File file = new File(profile.getSentFolder().getContainer().getContainerPath().concat("/").concat(ID).concat(".json"));
        if(!file.createNewFile()){
            throw new Exception("can not create sent");
        }
        sentEmail.setEmailID(ID);

        sentEmail.setOwner(email.getOwner());

        sentEmail.setEmailType("Sent");
        ObjectMapper map = new ObjectMapper();
        map.writeValue(file, sentEmail);
        System.out.println(sentEmail.getReceiverNames().get(0));
        return sentEmail;
    }


    public Email createEmailDataDraft(Email email, Profile profile, String ID) throws Exception{
        Email draftEmail = new Email(email.getSenderName(), email.getReceiverNames(), email.getOwner(), email.getSubject(), email.getBody(), email.getTimeSent(), email.getPriority(), email.getEmailID(), "Draft", email.getAttachments());
        File file = new File(profile.getDraftFolder().getContainer().getContainerPath().concat("/").concat(ID).concat(".json"));
        if(!file.createNewFile()){
            throw new Exception("can not create draft");
        }
        draftEmail.setEmailID(ID);
        draftEmail.setOwner(email.getOwner());
        ObjectMapper map = new ObjectMapper();
        map.writeValue(file, draftEmail);
        Database.getInstance().getProfileByName("", email.getOwner()).getDraftFolder().addEmail(draftEmail);
        return draftEmail;
    }


    public Email createEmailDataTrash(Email email, Profile profile, String ID) throws Exception{
        Email trashEmail = new Email(email.getSenderName(), email.getReceiverNames(), email.getOwner(), email.getSubject(), email.getBody(), email.getTimeSent(), email.getPriority(), email.getEmailID(), "Trash", email.getAttachments());
        File file = new File(profile.getTrashFolder().getContainer().getContainerPath().concat("/").concat(ID).concat(".json"));
        if(!file.createNewFile()){
            throw new Exception("can not create trash");
        }
        trashEmail.setEmailID(ID);
        trashEmail.setOwner(email.getOwner());
        trashEmail.setEmailType(email.getEmailType());
        ObjectMapper map = new ObjectMapper();
        map.writeValue(file, trashEmail);
        return trashEmail;
    }



    public Email createEmailDataProfileFolder(Email email, Profile profile, String folderName, String ID) throws Exception {
        if(profile.getProfileFolderbyName(folderName) == null){
            throw new Exception("folder doesn't exist");
        }
        Email emailFolder = new Email(email.getSenderName(), email.getReceiverNames(), email.getOwner(), email.getSubject(), email.getBody(), email.getTimeSent(), email.getPriority(), email.getEmailID(), folderName, email.getAttachments());
        File file = new File(profile.getProfileFolderbyName(folderName).getContainer().getContainerPath().concat("/").concat(ID).concat(".json"));
        if(!file.createNewFile()){
            throw new Exception("can not create folder");
        }
        emailFolder.setOwner(email.getOwner());
        emailFolder.setEmailID(ID);
        emailFolder.setEmailType(folderName);
        ObjectMapper map = new ObjectMapper();
        map.writeValue(file, emailFolder);
        return emailFolder;
    }

    public void createContactData(Profile profile, Contact contact) throws Exception{
        File file = new File(profile.getContacts().getContactsContainer().getContainerPath().concat("/").concat(contact.getName().concat(".json")));
        if(!file.createNewFile()){
            throw new Exception("can not create contact folder");
        }
        ObjectMapper map = new ObjectMapper();
        map.writeValue(file, contact);
    }
}

