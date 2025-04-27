package myy803.socialbookstore.datamodel.searchstrategies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.services.UserProfileService;
import myy803.socialbookstore.services.UserService;

@Component
public abstract class AbstractSearchStrategy implements SearchStrategy {

	@Autowired
	UserService userService;
	
	@Autowired
	UserProfileService userProfileService;

	@Override
	public ArrayList<BookDto> search(SearchDto searchDto) {
		ArrayList<BookDto> bookDtos = new ArrayList<BookDto>();
		
		if(searchDto.getTitle() != null) {
			List<Book> books = makeInitialListOfBooks(searchDto);
			
			boolean authorsMatch = true;
			
			for(Book book : books) {
				if(!searchDto.getAuthors().equals("")) {
					authorsMatch = checkIfAuthorsMatch(searchDto, book);
				}
				if(authorsMatch) bookDtos.add(book.buildDto());
			}
		}
		//extra block of code in order to not return the books the user himself provided or the ones he already requested
		String username = userService.getUsername();
		UserProfile userProfile = userProfileService.findProfileByUsername(username);
		List<BookDto> notTheseBooks = userProfile.buildBookOffersDtos();
		notTheseBooks.addAll(userProfile.buildBookRequestsDtos());
		return (ArrayList<BookDto>) removeUneededBooks(bookDtos, notTheseBooks);
	}
	
	private List<BookDto> removeUneededBooks(List<BookDto> bookDtos, List<BookDto> notTheseBooks) {
		//extra function in order to not return the books the user himself provided or the ones he already requested
		List<Integer> notTheseBookIds = getUneededBookIds(notTheseBooks);
		
		List<Integer> seenIds = new ArrayList<>();
		Iterator<BookDto> iterator = bookDtos.iterator();
		while (iterator.hasNext()) {
			BookDto book = iterator.next();
			if (notTheseBookIds.contains(book.getId()) || seenIds.contains(book.getId())) {
				iterator.remove();
			} else {
				seenIds.add(book.getId());
			}
		}
		
		return bookDtos;
	}
	
	private List<Integer> getUneededBookIds(List<BookDto> notTheseBooks) {
		//extra function in order to not return the books the user himself provided or the ones he already requested
		List<Integer> notTheseBookIds = new ArrayList<>();
		for (BookDto book : notTheseBooks) {
			notTheseBookIds.add(book.getId());
		}
		
		return notTheseBookIds;
	}
	
	protected abstract List<Book> makeInitialListOfBooks(SearchDto searchDto);
	
	protected abstract boolean checkIfAuthorsMatch(SearchDto searchDto, Book book);

}
