package myy803.socialbookstore.datamodel.searchstrategies;

import java.util.ArrayList;

import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.SearchDto;
import myy803.socialbookstore.mappers.BookMapper;

public interface SearchStrategy {
	ArrayList<BookDto> search(SearchDto bookDto);
}
