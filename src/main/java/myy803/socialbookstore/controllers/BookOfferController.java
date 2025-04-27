package myy803.socialbookstore.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.services.BookCategoryService;
import myy803.socialbookstore.services.BookService;
import myy803.socialbookstore.services.UserProfileService;

@Controller
public class BookOfferController {
	
	@Autowired
	private UserProfileService userProfileService;
	
	@Autowired 
	private BookCategoryService bookCategoryService;
	
	@Autowired
	private BookService bookService;
	
	@RequestMapping("/user/offers") 
    public String listBookOffers(Model model) {
		UserProfile userProfile = userProfileService.findProfile();
		
		List<BookDto> bookOffersDtos = userProfile.buildBookOffersDtos();
			
    	model.addAttribute("offers", bookOffersDtos);
    	
    	return "user/offers";
    }
    
    @RequestMapping("/user/show_offer_form") 
    public String showOfferForm(Model model) {
    	List<BookCategory> categories = bookCategoryService.getAllBookCategories();
    	
    	model.addAttribute("categories", categories);
    	model.addAttribute("offer", new BookDto());
    	
    	return "user/offer-form";
    }
    
    @RequestMapping("/user/save_offer") 
    public String saveOffer(@ModelAttribute("offer") BookDto bookOfferDto, Model model) {
    	UserProfile userProfile = userProfileService.findProfile();
    	
    	userProfileService.addBookOfferToUserProfile(bookOfferDto, userProfile);
		userProfileService.saveProfile(userProfile);
		
		return "redirect:/user/offers";
    }
    
    @RequestMapping("/user/delete_book_offer")
    public String deleteBookOffer(@RequestParam("selected_offer_id") int bookId, Model model) {
    	
    	bookService.deleteBook(bookId);
    	
    	return "redirect:/user/dashboard";
    }
}
