package myy803.socialbookstore.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import myy803.socialbookstore.datamodel.User;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.services.UserProfileService;
import myy803.socialbookstore.services.UserService;


@Controller
public class AuthController {
    @Autowired
    UserService userService;
    
    @Autowired
    UserProfileService userProfileService;

    @RequestMapping("/login")
    public String login(){
        return "auth/login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
       
        if(userService.isUserPresent(user)){
            model.addAttribute("successMessage", "User already registered!");
            return "auth/login";
        }

        userService.saveUser(user);
        
        UserProfile profile = new UserProfile();
        profile.setUsername(user.getUsername()); //extra block in order to always have a blank user_profile
        userProfileService.saveProfile(profile);
        
        model.addAttribute("successMessage", "User registered successfully!");

        return "auth/login";
    }
}
