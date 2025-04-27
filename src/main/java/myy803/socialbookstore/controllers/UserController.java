package myy803.socialbookstore.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import myy803.socialbookstore.services.UserProfileService;

@Controller
public class UserController {
	
	@Autowired
	private UserProfileService userProfileService;
	
    @RequestMapping("/user/dashboard")
    public String getUserMainMenu(Model model){
    	List<String> notifications = userProfileService.getUserNotifications();
    	model.addAttribute("notifications", notifications);
    	return "user/dashboard";
    }

}
