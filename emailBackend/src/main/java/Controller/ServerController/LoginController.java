package Controller.ServerController;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Controller.Database;
import Controller.Profile.Profile;

@RestController
@CrossOrigin(origins = "http://localhost:4200/login")

public class LoginController {
    //the request when loging in will be mapped right here
    @GetMapping("/login")
    Profile login(@RequestParam(value = "loginKey") String loginKey) {
        System.out.println(loginKey);
        try {
            Database database = Database.getInstance();
            return database.getProfileByKey(loginKey);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}




