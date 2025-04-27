package myy803.socialbookstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.mappers.BookCategoryMapper;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {
	
	@Autowired
	private BookCategoryMapper bookCategoryMapper;

	@Override
	public List<BookCategory> getAllBookCategories() {
		// TODO Auto-generated method stub
		return bookCategoryMapper.findAll();
	}

}
