package myy803.socialbookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.formsdata.BookDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.socialbookstore.mappers.BookMapper;
import myy803.socialbookstore.services.BookServiceImpl;

@ExtendWith(MockitoExtension.class)

public class BookServiceImplTest {

	
	 	@Mock
	    private BookMapper bookMapper; 

	    @InjectMocks
	    private BookServiceImpl bookService; 
	    
	  
	    @Test
	    void saveBookTest() {
	        Book book = new Book("TestBook");

	        bookService.saveBook(book);

	        verify(bookMapper).save(book);
	    }
	    
	    
	    @Test
	    void findByBookIdTest() {
	        int bookId = 1;
	        
	        Book expectedBook = new Book("TestBook");
	        
	        when(bookMapper.findById(bookId)).thenReturn(Optional.of(expectedBook));

	        Optional<Book> result = bookService.findByBookId(bookId);

	        assertTrue(result.isPresent());
	        assertEquals(expectedBook, result.get());
	    }
	    
	    @Test
	    void deleteBookTest() {
	        int bookId = 1;
	        Book book = new Book("TestBook");

	        when(bookMapper.findById(bookId)).thenReturn(Optional.of(book));

	        bookService.deleteBook(bookId);

	        verify(bookMapper).findById(bookId);
	        verify(bookMapper).delete(book);
	    }
	    
	    @Test
	    void findAllBooksTest() {
	        BookCategory category1 = new BookCategory();
	        category1.setName("Category 1");
	        
	        BookCategory category2 = new BookCategory();
	        category2.setName("Category 2");

	        
	        Book book1 = new Book("Book 1");
	        book1.setCategory(category1);

	        Book book2 = new Book("Book 2");
	        book2.setCategory(category2);

	        List<Book> mockBooks = List.of(book1, book2);

	        when(bookMapper.findAll()).thenReturn(mockBooks);

	        List<BookDto> result = bookService.findAllBooks();

	        assertEquals(2, result.size());
	        assertEquals("Book 1", result.get(0).getTitle());
	        assertEquals("Category 1", result.get(0).getCategory());
	        assertEquals("Book 2", result.get(1).getTitle());
	        assertEquals("Category 2", result.get(1).getCategory());

	        verify(bookMapper).findAll();
	    }



}
