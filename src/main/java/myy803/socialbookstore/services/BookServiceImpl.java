package myy803.socialbookstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.BookMapper;

@Service
public class BookServiceImpl implements BookService {
	
	@Autowired
	private BookMapper bookMapper;

	@Override
	public void saveBook(Book book) {
		bookMapper.save(book);
	}

	@Override
	public Optional<Book> findByBookId(int bookId) {
		return bookMapper.findById(bookId);
	}

	@Override
	public void deleteBook(int bookId) {
		Optional<Book> book = findByBookId(bookId);
		bookMapper.delete(book.get());
	}

	@Override
	public List<BookDto> findAllBooks() {
		List<BookDto> bookDtos = new ArrayList<>();
		List<Book> books = bookMapper.findAll();
		for (Book book : books) {
			bookDtos.add(book.buildDto());
		}
		return bookDtos;
	}
}
