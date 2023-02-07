package Controller.ServerController;


import Controller.Profile.Components.Email.Attachment;
import Controller.Profile.Components.Email.Email;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import Controller.RequestManager.SendEmailHandle;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class ComposeController {
    @PostMapping("/sendEmail")
    String sendEmail(@RequestBody Email email) {
        System.out.println("hi from send email");
        System.out.println(email.getReceiverNames().get(0));
        System.out.println(email.getSenderName());
        System.out.println(email.getOwner());
        System.out.println(email.getSubject());
        try{
            SendEmailHandle.getInstance().handle("sendEmail",email,"");
            return "SUCCESS";
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }


    @PostMapping("/sendWithAttachments")
    void attachment(@RequestParam(value = "email") String email, @RequestParam("file") MultipartFile[] files) {
        try {
            ObjectMapper map = new ObjectMapper();
            Email newEmail = map.readValue(email, Email.class);
            newEmail.setAttachments(new ArrayList<Attachment>());
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    Attachment attachment = new Attachment();
                    attachment.setEncodedFile(files[i].getBytes());
                    System.out.println((files[i].getBytes()));

                    System.out.println(files[i].getOriginalFilename());

                    attachment.setName(files[i].getOriginalFilename());

                    System.out.println(files[i].getSize());

                    attachment.setType(files[i].getContentType());
                    System.out.println(files[i].getContentType());
                    newEmail.addAttachment(attachment);

                    System.out.println(attachment.getName());
                }
            }
            SendEmailHandle.getInstance().handle("sendEmail",newEmail,"");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @PostMapping("/movetoDraft")
    String movetoDraft(@RequestBody Email email){
        try {
            SendEmailHandle.getInstance().handle("moveDraft",email,"");
            return "SUCCESS";
        }catch (Exception e){
            System.out.println(e.getMessage());
            return e.getMessage();
        }
    }

}


