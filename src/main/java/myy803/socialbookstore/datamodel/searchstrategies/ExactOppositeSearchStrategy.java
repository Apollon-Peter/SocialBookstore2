package myy803.socialbookstore.datamodel.searchstrategies;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.mappers.BookMapper;

@Component
public class ExactOppositeSearchStrategy extends AbstractSearchStrategy{
	
	@Autowired
	protected BookMapper bookMapper;

	@Override
	protected List<Book> makeInitialListOfBooks(SearchDto searchDto) {
		List<Book> books = bookMapper.findByTitle(searchDto.getTitle());
		List<Book> allBooks = bookMapper.findAll();
		
		allBooks.removeAll(books);
		
		return allBooks;
	}

	@Override
	protected boolean checkIfAuthorsMatch(SearchDto searchDto, Book book) {
		boolean authorsMatch;
		authorsMatch = !book.writtenBy(searchDto.getAuthors());
		return authorsMatch;
	}
	
}
