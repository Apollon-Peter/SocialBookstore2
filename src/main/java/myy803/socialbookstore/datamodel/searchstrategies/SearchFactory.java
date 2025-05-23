package myy803.socialbookstore.datamodel.searchstrategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SearchFactory {

	@Autowired
	private ExactSearchStrategy exactSearchStrategy;
	@Autowired
	private ApproximateSearchStrategy approximateSearchStrategy;
	@Autowired
	private ExactOppositeSearchStrategy negativeSearchStrategy;
	
	public SearchStrategy create(String searchStrategy) {
		if(searchStrategy.equals("Exact"))
			return exactSearchStrategy;
		else
			if (searchStrategy.equals("Exact Opposite"))
				return negativeSearchStrategy;
			else
				return approximateSearchStrategy;
	}
}
