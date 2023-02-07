package Controller.ServerController;


import Controller.ContactFilter.SearchContactCriteria;
import Controller.Creator;
import Controller.Database;
import Controller.Delete;
import Controller.Profile.Components.Contacts.Contact;
import Controller.Sort.ISort;
import Controller.Sort.SortContacts;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ContactsController {

    @PostMapping("/addContact")
    String addContact(@RequestParam(value = "username") String username, @RequestParam(value = "contactName") String contactName, @RequestBody ArrayList<String> contactEmails){
        try {
            System.out.println("new username:");
            System.out.println(username);
            System.out.println("new contact username:");
            System.out.println(contactName);
            System.out.println(Arrays.deepToString(contactEmails.toArray(new String[0])));
            Database database = Database.getInstance();
            Contact newCon = new Contact();
            newCon.setEmails(contactEmails);
            newCon.setName(contactName);
            Creator.getInstance().createContactData(database.getProfileByName("", username), newCon);
            database.getProfileByName("",username).getContacts().addContact(newCon);
            return "Contact Created Successfully";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    @GetMapping ("/getContacts")
    ArrayList<Contact> getContacts(@RequestParam(value = "username") String username){
        try {
            Database database = Database.getInstance();
            return database.getProfileByName("", username).getContacts().getContacts();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/removeContact")
    String removeContact(@RequestParam(value = "username") String username, @RequestBody Contact[] contacts){
        System.out.println("hy from remove");
        for(int i = 0; i < contacts.length; i++) {
            System.out.println("us: " + username);
            System.out.println(contacts[i].getName());
            System.out.println("emails" + contacts[i].getEmails().get(0));
            try {
                Database database = Database.getInstance();
                Delete.getInstance().deleteContact(database.getProfileByName("", username).getContacts().getContact(contacts[i].getName()), database.getProfileByName("", username));
                database.getProfileByName("", username).getContacts().deleteContact(contacts[i].getName());

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
        }
        return "Contact Deleted Successfully";
    }

    @GetMapping("/sortContacts")
    ArrayList<Contact> sortContacts(@RequestParam(value = "username") String username, @RequestParam(value = "ascending") String ascending) {
        try {
            SortContacts sorting = new SortContacts(Boolean.parseBoolean(ascending));
            return sorting.sort(Database.getInstance().getProfileByName("", username).getContacts().getContacts());

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/searchContacts")
    ArrayList<Contact> searchContacts(@RequestParam(value = "username") String username,@RequestParam(value = "target") String target){
        try {
            Database database = Database.getInstance();
            SearchContactCriteria search = new SearchContactCriteria(target);
            return search.FilteredContact(database.getProfileByName("",username).getContacts().getContacts());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
