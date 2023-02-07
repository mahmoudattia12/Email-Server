package Controller.ServerController;

import Controller.Creator;
import Controller.Database;
import Controller.Delete;
import Controller.EmailFilter.EmailFilter;
import Controller.EmailFilter.IEmailCriteria;
import Controller.EmailFilter.SearchFiltered;
import Controller.Profile.Components.Email.Email;
import Controller.Profile.Components.UserFolder;
import Controller.RequestManager.SendEmailHandle;
import Controller.Sort.SortEmails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class FolderController {

    @GetMapping("/getOneFolder")
    ArrayList<Email> getOneFolder(@RequestParam(value = "username") String username, @RequestParam(value = "foldername") String folderName){
        try{
            SortEmails emailsSorter = new SortEmails(false);
            return emailsSorter.SortMails(Database.getInstance().getProfileByName("", username).getProfileFolderbyName(folderName).getEmails(), "Date");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/getFolders")
    ArrayList<UserFolder> getAllFolders(@RequestParam(value = "username") String username){
        try{
            return Database.getInstance().getProfileByName("", username).getFolders();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/addFolder")
    String addUserFolder(@RequestParam("username") String username, @RequestBody String folderName){

        try{
            Creator.getInstance().createProfileFolder(folderName, Database.getInstance().getProfileByName("", username));
            return "Folder Added Successfully";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/moveToFolder")
    String moveToFolder(@RequestBody Email[] emails, @RequestParam(value = "username") String username, @RequestParam(value = "foldername") String folderName){
        for(int i = 0; i < emails.length; i++) {
            try {
                SendEmailHandle.getInstance().handle("moveFolder", emails[i], folderName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return e.getMessage();
            }
        }
        return "Moved To Folder Successfully";

    }

    @DeleteMapping("/removeFolder")
    String removeFolder(@RequestParam("username") String username, @RequestParam(value = "foldername") String foldername){
        try{
            Delete.getInstance().deleteProfileFolder(Database.getInstance().getProfileByName("", username), foldername);
            return "Folder Removed Successfully";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

    @PostMapping("/moveToTrashFolder")
    ArrayList<Email> moveToTrash(@RequestBody Email[] emails, @RequestParam(value = "foldername") String folderName){
        for(int i = 0; i < emails.length; i++) {
            try {
                SendEmailHandle.getInstance().handle("moveTrash", emails[i], folderName);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        try{
            return Database.getInstance().getProfileByName("", emails[0].getOwner()).getProfileFolderbyName(folderName).getEmails();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/filterFolder")
    ArrayList<Email> filterFolder(@RequestParam(value = "username") String username, @RequestParam(value = "foldername") String folderName, @RequestParam(value = "target") String target, @RequestParam(value = "feature") String feature){
        try{
            Database database = Database.getInstance();
            IEmailCriteria filter = new EmailFilter(feature, target);
            return filter.meetCriteria(database.getProfileByName("", username).getProfileFolderbyName(folderName).getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/sortFolder")
    ArrayList<Email> sortFolder(@RequestParam(value = "username") String username, @RequestParam(value = "foldername") String folderName, @RequestParam(value = "target") String target, @RequestParam(value = "ascending") String ascending){
        try{
            Database database = Database.getInstance();
            SortEmails sorter = new SortEmails(Boolean.parseBoolean(ascending));
            return sorter.SortMails(database.getProfileByName("", username).getProfileFolderbyName(folderName).getEmails(), target);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping("/searchFolder")
    ArrayList<Email> searchfolder(@RequestParam(value = "username") String username, @RequestParam(value = "foldername") String folderName, @RequestParam(value = "target") String target){
        try{
            Database database = Database.getInstance();
            IEmailCriteria searcher = new SearchFiltered(target);
            return searcher.meetCriteria(database.getProfileByName("", username).getProfileFolderbyName(folderName).getEmails());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


}
