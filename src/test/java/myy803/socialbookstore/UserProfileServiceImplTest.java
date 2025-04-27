package myy803.socialbookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import myy803.socialbookstore.datamodel.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.mappers.UserProfileMapper;
import myy803.socialbookstore.services.BookService;
import myy803.socialbookstore.services.UserProfileServiceImpl;
import myy803.socialbookstore.services.UserService;

@ExtendWith(MockitoExtension.class)

public class UserProfileServiceImplTest {

	 	@Mock
	    private UserProfileMapper userProfileMapper;

	    @Mock
	    private UserService userService;

	    @InjectMocks
	    private UserProfileServiceImpl userProfileService;
	    private UserProfile userProfile;
	    private BookDto bookDto;
	    private UserProfileDto userProfileDto;

	
	    @Mock
	    private BookAuthorMapper bookAuthorMapper;

	    @Mock
	    private BookCategoryMapper bookCategoryMapper;
	    
	    @Mock
	    private BookService bookService;


	   

	    @Test
	    void findProfileTest() {
	        String mockUsername = "testUser";
	        UserProfile mockUserProfile = new UserProfile();
	        mockUserProfile.setUsername(mockUsername);

	        when(userService.getUsername()).thenReturn(mockUsername);
	        when(userProfileMapper.findById(mockUsername)).thenReturn(Optional.of(mockUserProfile));

	        UserProfile result = userProfileService.findProfile();

	        assertEquals(mockUserProfile, result);

	        verify(userService).getUsername();
	        verify(userProfileMapper).findById(mockUsername);
	    }
	  
	    @Test
	    void saveProfileTest() {
	        UserProfile mockUserProfile = new UserProfile();
	        mockUserProfile.setUsername("testUser");

	        userProfileService.saveProfile(mockUserProfile);

	        verify(userProfileMapper).save(mockUserProfile);
	    }
	    
	    @Test
	    void findOrCreateProfileTest() {
	        String mockUsername = "testUser";
	        UserProfile mockUserProfile = mock(UserProfile.class); // Δημιουργία mock αντικειμένου
	        UserProfileDto mockUserProfileDto = new UserProfileDto();
	        mockUserProfileDto.setUsername(mockUsername);

	        when(userService.getUsername()).thenReturn(mockUsername);
	        when(userProfileMapper.findById(mockUsername)).thenReturn(Optional.of(mockUserProfile));
	        when(mockUserProfile.buildProfileDto()).thenReturn(mockUserProfileDto); // Mock η μέθοδος buildProfileDto

	        UserProfileDto result = userProfileService.findOrCreateProfile();

	        assertEquals(mockUserProfileDto, result);

	        verify(userService).getUsername();
	        verify(userProfileMapper).findById(mockUsername);
	        verify(mockUserProfile).buildProfileDto();
	    }
	    
	    @Test
	    void createOrUpdateProfileTest() {
	        userProfileDto = mock(UserProfileDto.class);
	        userProfile = new UserProfile();

	        when(userProfileDto.getUsername()).thenReturn("testUser");

	        when(userProfileMapper.findById("testUser")).thenReturn(Optional.of(userProfile));

	        doNothing().when(userProfileDto).buildUserProfile(userProfile, bookAuthorMapper, bookCategoryMapper);

	        UserProfile result = userProfileService.createOrUpdateProfile(userProfileDto);

	        assertNotNull(result);
	        assertEquals(userProfile, result);
	        verify(userProfileMapper, times(1)).findById("testUser");
	        verify(userProfileDto, times(1)).buildUserProfile(userProfile, bookAuthorMapper, bookCategoryMapper);
	    }

	    
	    @Test
	    void addBookOfferToUserProfileTest() {
	    	Book book = new Book();
	        userProfile = new UserProfile();
	        bookDto = mock(BookDto.class);
	        
	        when(bookDto.buildBookOffer(bookAuthorMapper, bookCategoryMapper)).thenReturn(book);

	        // Ensure the user profile starts with no books
	        assertTrue(userProfile.getBookOffers().isEmpty());

	        userProfileService.addBookOfferToUserProfile(bookDto, userProfile);

	        assertEquals(1, userProfile.getBookOffers().size());
	        assertTrue(userProfile.getBookOffers().contains(book));

	        // Verify the interaction with bookDto
	        verify(bookDto, times(1)).buildBookOffer(bookAuthorMapper, bookCategoryMapper);
	    }
	
	    
	    @Test
	    void findProfileByUsernameTest() {
	        String mockUsername = "testUser";
	        UserProfile mockUserProfile = new UserProfile();
	        mockUserProfile.setUsername(mockUsername);

	        when(userProfileMapper.findById(mockUsername)).thenReturn(Optional.of(mockUserProfile));

	        UserProfile result = userProfileService.findProfileByUsername(mockUsername);

	        assertNotNull(result);
	        assertEquals(mockUserProfile, result);
	        
	        verify(userProfileMapper).findById(mockUsername);
	    }
	    
	    
	    @Test
	    void findOptProfileTest() {
	        String mockUsername = "testUser";
	        UserProfile mockUserProfile = new UserProfile();
	        mockUserProfile.setUsername(mockUsername);

	        when(userService.getUsername()).thenReturn(mockUsername);
	        when(userProfileMapper.findById(mockUsername)).thenReturn(Optional.of(mockUserProfile));

	        Optional<UserProfile> result = userProfileService.findOptProfile();

	        
	        assertTrue(result.isPresent());
	        assertEquals(mockUserProfile, result.get());
	        
	        verify(userService).getUsername();
	        verify(userProfileMapper).findById(mockUsername);
	    }
	    
	    @Test
	    void getUserNotificationsTest() {
	        UserProfile mockUserProfile = new UserProfile();
	        List<String> mockNotifications = new ArrayList<>();
	        mockNotifications.add("Notification 1");
	        mockNotifications.add("Notification 2");
	        mockNotifications.add("Notification 3");

	        mockUserProfile.setNotifications(mockNotifications);

	        when(userProfileService.findOptProfile()).thenReturn(Optional.of(mockUserProfile));

	        List<String> result = userProfileService.getUserNotifications();

	        List<String> expected = new ArrayList<>(mockNotifications);
	        assertEquals(expected, result);
	    }
	    
	    
	    @Test
	    void createNotificationsTest() {

	        UserProfile acceptedUserProfile = new UserProfile();
	        acceptedUserProfile.setUsername("acceptedUser");
	        acceptedUserProfile.setFullName("Accepted User");
	        acceptedUserProfile.setAge(25);
	        acceptedUserProfile.setNotifications(new ArrayList<>());

	        UserProfile ownerUserProfile = new UserProfile();
	        ownerUserProfile.setUsername("ownerUser");
	        ownerUserProfile.setNotifications(new ArrayList<>());

	        UserProfile otherUser = new UserProfile();
	        otherUser.setUsername("otherUser");
	        otherUser.setNotifications(new ArrayList<>());

	        int bookId = 0;
	        Book book = new Book();
	        book.setTitle("Test Book");
	        List<UserProfile> requestingUsers = new ArrayList<>();
	        requestingUsers.add(acceptedUserProfile);
	        requestingUsers.add(otherUser);
	        book.setRequestingUsers(requestingUsers);
	        
	        when(userService.getUsername()).thenReturn(ownerUserProfile.getUsername());
	        when(bookService.findByBookId(bookId)).thenReturn(Optional.of(book));
	        when(userProfileMapper.findById("acceptedUser")).thenReturn(Optional.of(acceptedUserProfile));
	        when(userProfileMapper.findById("ownerUser")).thenReturn(Optional.of(ownerUserProfile));
	      
	        userProfileService.createNotifications("acceptedUser", bookId);

	        String acceptedMessage = "Your book request for the book: \"Test Book\", has been accepted by the owner!";
	        String infoMessage = "You accepted a request for the book \"Test Book\", from the following user:   Full Name: Accepted User,   Age: 25";
	        String declineMessage = "The book you requested with the title \"Test Book\", is no longer available!";

	        assertTrue(acceptedUserProfile.getNotifications().contains(acceptedMessage));
	        assertTrue(ownerUserProfile.getNotifications().contains(infoMessage));
	        assertTrue(otherUser.getNotifications().contains(declineMessage));

	        verify(userProfileMapper).save(acceptedUserProfile);
	        verify(userProfileMapper).save(ownerUserProfile);
	        verify(userProfileMapper).save(otherUser);
	    }
	  

}
