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
public class SentController {
    @GetMapping("/getSent")
    ArrayList<Email> getSent(@RequestParam(value = "username") String username){
        try{
            SortEmails emailsSorter = new SortEmails(false);
            return emailsSorter.SortMails(Database.getInstance().getProfileByName("", username).getSentFolder().getEmails(), "Date");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/movetoTrashSent")
    ArrayList<Email> movetoTrash(@RequestBody Email[] emails){
        for(int i = 0; i < emails.length; i++){
            try{
                SendEmailHandle.getInstance().handle("moveTrash", emails[i],"");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        try {
            return Database.getInstance().getProfileByName("", emails[0].getOwner()).getSentFolder().getEmails();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/sortSent")
    ArrayList<Email> sortSent(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target, @RequestParam(value = "ascending") String ascending){
        try{
            Database database = Database.getInstance();
            SortEmails sorter = new SortEmails(Boolean.parseBoolean(ascending));
            return sorter.SortMails(database.getProfileByName("", username).getSentFolder().getEmails(), target);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/filterSent")
    ArrayList<Email> filterSent(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target, @RequestParam(value = "feature") String feature){
        try{
            Database database = Database.getInstance();
            IEmailCriteria filter = new EmailFilter(feature, target);
            return filter.meetCriteria(database.getProfileByName("", username).getSentFolder().getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/searchSent")
    ArrayList<Email> searchSent(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target){
        try{
            Database database = Database.getInstance();
            IEmailCriteria searcher = new SearchFiltered(target);
            return searcher.meetCriteria(database.getProfileByName("", username).getSentFolder().getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
