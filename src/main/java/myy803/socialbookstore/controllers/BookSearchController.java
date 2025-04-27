package myy803.socialbookstore.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import myy803.socialbookstore.datamodel.searchstrategies.SearchFactory;
import myy803.socialbookstore.datamodel.searchstrategies.SearchStrategy;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.SearchDto;

@Controller
public class BookSearchController {
	
	@Autowired
	private SearchFactory searchFactory;
	
	@RequestMapping("/user/search")
    public String showSearchForm(Model model) {
    	SearchDto searchDto = new SearchDto();
    	
    	model.addAttribute("searchDto", searchDto);
    	
    	return "user/search_form";
    }
    
    @RequestMapping("/user/search_offer")
    public String search(@ModelAttribute("searchDto") SearchDto searchDto, Model model) {
    	SearchStrategy searchStrategy = searchFactory.create(searchDto.getSelectedStrategy());
    	List<BookDto> bookDtos = searchStrategy.search(searchDto);
		
    	model.addAttribute("books", bookDtos);
    	
    	return "user/search_results";
    }
}
