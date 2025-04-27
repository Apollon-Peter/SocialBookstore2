package myy803.socialbookstore.formsdata;

import java.util.List;

import org.springframework.stereotype.Component;

import myy803.socialbookstore.datamodel.Book;
import myy803.socialbookstore.datamodel.BookAuthor;
import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.mappers.BookAuthorMapper;
import myy803.socialbookstore.mappers.BookCategoryMapper;

@Component
public class BookDto {
	private int id;
	private String title;
	private String authors;
	private String category;
	
	private String searchStrategy;
	
	public BookDto() {
		super();
	}

	public BookDto(int offerId, String bookTitle, String bookAuthors, String bookCategory) {
		super();
		this.id = offerId;
		this.title = bookTitle;
		this.authors = bookAuthors;
		this.category = bookCategory;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	
	public String getSearchStrategy() {
		return searchStrategy;
	}

	public void setSearchStrategy(String searchStrategy) {
		this.searchStrategy = searchStrategy;
	}

	@Override
	public String toString() {
		return "BookOfferDto [offerId=" + id + ", bookTitle=" + title + ", bookAuthors=" + authors
				+ ", bookCategory=" + category + "]";
	}

	public Book buildBookOffer(BookAuthorMapper bookAuthorMapper, BookCategoryMapper bookCategoryMapper) {
		Book bookOffer = new Book(title);
		
		List<BookCategory> bookCategories = bookCategoryMapper.findByName(category);
		if(bookCategories.size() == 0) {
			bookCategories = bookCategoryMapper.findByName("Other");
		}

		bookOffer.setCategory(bookCategories.get(0));
	
		String[] bookAuthorsArray = authors.split(",");
		for(int i = 0; i < bookAuthorsArray.length; i++) {
			List<BookAuthor> bookAuthors = bookAuthorMapper.findByName(bookAuthorsArray[i].trim());
			if(bookAuthors.size() != 0)
				bookOffer.addAuthor(bookAuthors.get(0));
			else
				bookOffer.addAuthor(new BookAuthor(bookAuthorsArray[i].trim()));
		}
		
		return bookOffer;
	}
	
}
