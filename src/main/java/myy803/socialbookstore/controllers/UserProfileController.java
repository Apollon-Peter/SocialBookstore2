package myy803.socialbookstore.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.services.BookCategoryService;
import myy803.socialbookstore.services.UserProfileService;

@Controller
public class UserProfileController {
	
	@Autowired
	private BookCategoryService bookCategoryService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@RequestMapping("/user/profile")
    public String retrieveProfile(Model model){    	
    	List<BookCategory> categories = bookCategoryService.getAllBookCategories();
    	model.addAttribute("categories", categories);
    	
    	UserProfileDto userProfileDto = userProfileService.findOrCreateProfile();
    	model.addAttribute("profile", userProfileDto);
    	
    	return "user/profile";
    }
    
    @RequestMapping("/user/save_profile")
    public String saveProfile(@ModelAttribute("profile") UserProfileDto userProfileDto, Model theModel) {    	    	
    	UserProfile userProfile = userProfileService.createOrUpdateProfile(userProfileDto);
		
		userProfileService.saveProfile(userProfile);
		
    	return "user/dashboard";
    }
}
