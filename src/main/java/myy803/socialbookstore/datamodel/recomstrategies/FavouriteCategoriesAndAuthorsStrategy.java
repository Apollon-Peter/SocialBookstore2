package myy803.socialbookstore.datamodel.recomstrategies;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;

@Component
public class FavouriteCategoriesAndAuthorsStrategy extends AbstractRecommendationStrategy {	
	
	@Override
	protected List<BookDto> retrieveRecommendedBooks(UserProfile userProfile) {
		ArrayList<BookDto> bookDtos = new ArrayList<BookDto>();
		bookDtos.addAll(userProfile.getBooksFromFavouriteAuthors());
		bookDtos.addAll(userProfile.getBooksOfFavouriteCategories());
		return bookDtos;
	}
}
