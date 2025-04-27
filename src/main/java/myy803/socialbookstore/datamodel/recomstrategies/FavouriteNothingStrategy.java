package myy803.socialbookstore.datamodel.recomstrategies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.services.BookService;

@Component
public class FavouriteNothingStrategy extends AbstractRecommendationStrategy{
	
	@Autowired
	private BookService bookService;

	@Override
	protected List<BookDto> retrieveRecommendedBooks(UserProfile userProfile) {
		
		List<BookDto> favouriteBookDtos = findAllFavouriteBooks(userProfile);
		List<BookDto> allBookDtos = bookService.findAllBooks();

		Iterator<BookDto> iterator = allBookDtos.iterator();
		while (iterator.hasNext()) {
			BookDto aBook = iterator.next();
			for (BookDto favouriteBook : favouriteBookDtos) {
				if (aBook.getId() == favouriteBook.getId()) {
					iterator.remove();
					break;
				}
			}
		}
		
		return allBookDtos;
	}
	
	private List<BookDto> findAllFavouriteBooks(UserProfile userProfile) {
		List<BookDto> favouriteBookDtos = new ArrayList<>();
		favouriteBookDtos.addAll(userProfile.getBooksFromFavouriteAuthors());
		favouriteBookDtos.addAll(userProfile.getBooksOfFavouriteCategories());
		return favouriteBookDtos;
	}

}
