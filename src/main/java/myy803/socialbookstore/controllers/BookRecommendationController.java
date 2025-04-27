package myy803.socialbookstore.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsFactory;
import myy803.socialbookstore.datamodel.recomstrategies.RecommendationsStrategy;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.formsdata.RecommendationsDto;
import myy803.socialbookstore.services.UserService;

@Controller
public class BookRecommendationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RecommendationsFactory recommendationsFactory;
	
	@RequestMapping("/user/recom")
    public String showRecommendationsForm(Model model) {
    	RecommendationsDto recomDto = new RecommendationsDto();
    	
    	model.addAttribute("recomDto", recomDto);
    	
    	return "user/recommendation_form";
    }
    
    @RequestMapping("/user/recommend_offers")
    public String recommend(@ModelAttribute("recomDto") RecommendationsDto recomDto, Model model) {
    	String username = userService.getUsername();
    	    	
    	RecommendationsStrategy recommendationsStrategy = recommendationsFactory.create(recomDto.getSelectedStrategy());
    	List<BookDto> bookDtos = recommendationsStrategy.recommend(username);
		
    	model.addAttribute("books", bookDtos);
    	
    	return "user/search_results";
    }
}
