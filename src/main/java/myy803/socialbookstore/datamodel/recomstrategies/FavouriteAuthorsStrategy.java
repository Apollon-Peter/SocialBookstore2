package myy803.socialbookstore.datamodel.recomstrategies;

import java.util.List;
import org.springframework.stereotype.Component;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;

@Component
public class FavouriteAuthorsStrategy extends AbstractRecommendationStrategy {
	
	@Override
	protected List<BookDto> retrieveRecommendedBooks(UserProfile userProfile) {
		List<BookDto> bookDtos = userProfile.getBooksFromFavouriteAuthors();
		return bookDtos;
	}
}
