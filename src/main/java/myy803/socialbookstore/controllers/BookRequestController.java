package myy803.socialbookstore.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.services.BookService;
import myy803.socialbookstore.services.UserProfileService;

@Controller
public class BookRequestController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserProfileService userProfileService;
	
	@RequestMapping("/user/request_book")
    public String request(@RequestParam("selected_book_id") int bookId, Model model) {
    	Optional<Book> requestedBook = bookService.findByBookId(bookId);
    	UserProfile userProfile = userProfileService.findProfile();
    	
		requestedBook.get().addRequestingUser(userProfile);
		bookService.saveBook(requestedBook.get());
    	
    	return "redirect:/user/dashboard";
    }
    
    @RequestMapping("/user/requests")
    public String showUserBookRequests(Model model) {
    	UserProfile userProfile = userProfileService.findProfile();
    	
		List <BookDto> requests = userProfile.buildBookRequestsDtos();
		
    	model.addAttribute("requests", requests);
    	
    	return "/user/book_requests";
    }

    @RequestMapping("/user/book_requesting_users")
    public String showRequestingUsersForBookOffer(@RequestParam("selected_offer_id") int bookId, Model model) {
    	Optional<Book> book = bookService.findByBookId(bookId);
    	
    	List<UserProfileDto> requestingUsers = book.get().getRequestingUserProfileDtos();
    	
    	model.addAttribute("requesting_users", requestingUsers);
    	model.addAttribute("book_id", bookId);
   
    	return "/user/requesting_users";
    }
    
    @RequestMapping("/user/accept_request")
    public String acceptRequest(@RequestParam("selected_user") String username, @RequestParam("book_id") int bookId, Model model) {
    	userProfileService.createNotifications(username, bookId);
    	return "redirect:/user/dashboard";
    }
    
    @RequestMapping("/user/delete_book_request")
    public String deleteBookRequest(@RequestParam("selected_request_id") int bookId, Model model) {
    	
    	Book book = bookService.findByBookId(bookId).get();
    	UserProfile userProfile = userProfileService.findProfile();
    	
    	book.deleteRequestingUser(userProfile);
    	bookService.saveBook(book);
    	
    	return "redirect:/user/dashboard";
    }
}
