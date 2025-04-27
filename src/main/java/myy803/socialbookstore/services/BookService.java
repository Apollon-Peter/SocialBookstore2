package myy803.socialbookstore.services;

import java.util.List;
import java.util.Optional;
import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.formsdata.BookDto;

public interface BookService {
	public void saveBook(Book book);
	public Optional<Book> findByBookId(int bookId);
	public void deleteBook(int bookId);
	public List<BookDto> findAllBooks();
}
