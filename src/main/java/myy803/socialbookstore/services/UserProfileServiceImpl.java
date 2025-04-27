package myy803.socialbookstore.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	@Autowired
	private BookAuthorMapper bookAuthorMapper;
	
	@Autowired
	private BookCategoryMapper bookCategoryMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BookService bookService;

	@Override
	public UserProfile findProfile() {
		String username = userService.getUsername();
		Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
		return optUserProfile.get();
	}

	@Override
	public void saveProfile(UserProfile profile) {
		userProfileMapper.save(profile);
	}

	@Override
	public UserProfileDto findOrCreateProfile() {
		String username = userService.getUsername();
		Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
		UserProfile userProfile = null; 
		UserProfileDto userProfileDto = null;
		
		if(optUserProfile.isPresent()) {
			userProfile = optUserProfile.get();
			userProfileDto = userProfile.buildProfileDto();
		} else {
			userProfileDto = new UserProfileDto();
			userProfileDto.setUsername(username);
		}
		
		return userProfileDto;
	}

	@Override
	public UserProfile createOrUpdateProfile(UserProfileDto userProfileDto) {
		
		Optional<UserProfile> optUserProfile = userProfileMapper.findById(userProfileDto.getUsername());
		UserProfile userProfile = null;
		
		if(optUserProfile.isPresent())	
			userProfile = optUserProfile.get();
		else
			userProfile = new UserProfile();
		
		userProfileDto.buildUserProfile(userProfile, bookAuthorMapper, bookCategoryMapper);
		
		return userProfile;
	}

	@Override
	public void addBookOfferToUserProfile(BookDto bookDto, UserProfile userProfile) {
		userProfile.addBookOffer(bookDto.buildBookOffer(bookAuthorMapper, bookCategoryMapper));
		
	}

	@Override
	public UserProfile findProfileByUsername(String username) {
		Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
		return optUserProfile.get();
	}

	@Override
	public Optional<UserProfile> findOptProfile() {
		String username = userService.getUsername();
		Optional<UserProfile> optUserProfile = userProfileMapper.findById(username);
		return optUserProfile;
	}
	
	@Override
	public List<String> getUserNotifications() {
		Optional<UserProfile> userProfile = findOptProfile();
    	
    	List<String> notifications = new ArrayList<>();
    	
    	if (!userProfile.isEmpty()) {
    		notifications = userProfile.get().getNotifications();
    	}
    	
    	Collections.reverse(notifications);
    	
    	return notifications;
	}

	@Override
	public void createNotifications(String username, int bookId) {
		UserProfile acceptedUserProfile = findProfileByUsername(username);
    	UserProfile ownerUserProfile = findProfile();
    	Book book = bookService.findByBookId(bookId).get();
    	List<UserProfile> otherUserProfile = book.getRequestingUsers();
    	
    	String acceptedMessage = "Your book request for the book: \"" + book.getTitle() + "\", has been accepted by the owner!";
    	String infoMessage = "You accepted a request for the book \"" + book.getTitle() + "\", from the following user:   Full Name: "
    						+ acceptedUserProfile.getFullName() + ",   Age: " + acceptedUserProfile.getAge();
    	String declineMessage = "The book you requested with the title \"" + book.getTitle() + "\", is no longer available!";
    	
    	acceptedUserProfile.addNotification(acceptedMessage);
    	saveProfile(acceptedUserProfile);
    	ownerUserProfile.addNotification(infoMessage);
    	saveProfile(ownerUserProfile);
    	for (UserProfile userProfile : otherUserProfile) {
    		if (userProfile.getUsername() != acceptedUserProfile.getUsername()) {
    			userProfile.addNotification(declineMessage);
    			saveProfile(userProfile);
    		}
    	}
	}

}
