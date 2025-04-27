package myy803.socialbookstore.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.socialbookstore.datamodel.Book;

public interface BookMapper extends JpaRepository<Book, Integer> {
	List<Book> findByTitle(String title);
	List<Book> findByTitleContaining(String title);
	void delete(Book book);
}
