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
public class TrashController {
    @GetMapping("/getTrash")
    ArrayList<Email> getTrash(@RequestParam(value = "username") String username){
        try{
            SortEmails emailsSorter = new SortEmails(false);
            return emailsSorter.SortMails(Database.getInstance().getProfileByName("", username).getTrashFolder().getEmails(), "Date");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @DeleteMapping("/delete")
    ArrayList<Email> deleteForever(@RequestBody Email[] emails){
        for(int i = 0; i < emails.length; i++) {
            try{
                SendEmailHandle.getInstance().handle("permanentDelete", emails[i], "");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

    @PostMapping("/restore")
    ArrayList<Email> restore(@RequestBody Email[] emails){
        for(int i = 0; i < emails.length; i++) {
            try{
                SendEmailHandle.getInstance().handle("restore", emails[i], "");
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }

    @GetMapping("/sortTrash")
    ArrayList<Email> sortTrash(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target, @RequestParam(value = "ascending") String ascending){
        try{
            Database database = Database.getInstance();
            SortEmails sorter = new SortEmails(Boolean.parseBoolean(ascending));
            return sorter.SortMails(database.getProfileByName("", username).getTrashFolder().getEmails(), target);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/filterTrash")
    ArrayList<Email> filterTrash(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target, @RequestParam(value = "feature") String feature){
        try{
            Database database = Database.getInstance();
            IEmailCriteria filter = new EmailFilter(feature, target);
            return filter.meetCriteria(database.getProfileByName("", username).getTrashFolder().getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/searchTrash")
    ArrayList<Email> searchTrash(@RequestParam(value = "username") String username, @RequestParam(value = "target") String target){
        try{
            Database database = Database.getInstance();
            IEmailCriteria searcher = new SearchFiltered(target);
            return searcher.meetCriteria(database.getProfileByName("", username).getTrashFolder().getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
