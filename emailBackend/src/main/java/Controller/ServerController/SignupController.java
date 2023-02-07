package Controller.ServerController;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import Controller.Database;

import org.springframework.web.bind.annotation.*;
@RestController
@CrossOrigin(origins = "http://localhost:4200")

public class SignupController {
	//the request when creating a new profile will be mapped right here
	@PostMapping("/signup")
	String signup(@RequestBody String loginKey) {
		System.out.println(loginKey);
		String result;
		try {
			Database database = Database.getInstance();
			database.addProfile(loginKey);
			result = "SUCCESS";
		}catch(Exception e){
	            System.out.println(e);
	            result = e.getMessage();
		}
		return result;
	}
	
}
