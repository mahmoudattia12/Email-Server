package Controller.ServerController;

import Controller.Database;
import Controller.EmailFilter.EmailFilter;
import Controller.EmailFilter.IEmailCriteria;
import Controller.EmailFilter.SearchFiltered;
import Controller.Profile.Components.Email.Email;
import Controller.RequestManager.SendEmailHandle;
import Controller.Sort.SortEmails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class InboxController {
    @GetMapping("/getInbox")
    ArrayList<Email> getInbox(@RequestParam(value = "username") String username){
        try{
            SortEmails emailsSorter = new SortEmails(false);
            System.out.println("size");
            System.out.println( emailsSorter.SortMails(Database.getInstance().getProfileByName("", username).getInboxFolder().getEmails(), "Date").size());
            return emailsSorter.SortMails(Database.getInstance().getProfileByName("", username).getInboxFolder().getEmails(), "Date");
        }
        catch (Exception e){
            System.out.println("error");
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/movetoTrashInbox")
    ArrayList<Email> movetoTrash(@RequestBody Email[] emails){
        for(int i = 0; i < emails.length; i++) {
            System.out.println(emails[i].getSenderName());
            try{
                SendEmailHandle.getInstance().handle("moveTrash", emails[i],"");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        try {
            return Database.getInstance().getProfileByName("", emails[0].getOwner()).getInboxFolder().getEmails();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/sortInbox")
    ArrayList<Email> sortInbox(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target, @RequestParam(value = "ascending") String ascending){
        try{
            Database database = Database.getInstance();
            SortEmails sorter = new SortEmails(Boolean.parseBoolean(ascending));
            return sorter.SortMails(database.getProfileByName("", username).getInboxFolder().getEmails(), target);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/filterInbox")
    ArrayList<Email> filterInbox(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target, @RequestParam(value = "feature") String feature){
        try{
            Database database = Database.getInstance();
            System.out.println("username" + username);
            System.out.println("target" + target);
            System.out.println("feat" + feature);

            IEmailCriteria filter = new EmailFilter(feature, target);
            return filter.meetCriteria(database.getProfileByName("", username).getInboxFolder().getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/searchInbox")
    ArrayList<Email> searchInbox(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target){
        try{
            Database database = Database.getInstance();
            IEmailCriteria searcher = new SearchFiltered(target);
            return searcher.meetCriteria(database.getProfileByName("", username).getInboxFolder().getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }



}
