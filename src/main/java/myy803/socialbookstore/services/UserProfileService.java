package myy803.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.UserProfileDto;

public interface UserProfileService {
	public UserProfile findProfile();
	public void saveProfile(UserProfile profile);
	public UserProfileDto findOrCreateProfile();
	public UserProfile createOrUpdateProfile(UserProfileDto userProfileDto);
	public void addBookOfferToUserProfile(BookDto bookDto, UserProfile userProfile);
	public UserProfile findProfileByUsername(String username);
	public Optional<UserProfile> findOptProfile();
	public void createNotifications(String username, int bookId);
	public List<String> getUserNotifications();
}
