package myy803.socialbookstore.datamodel.recomstrategies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import myy803.socialbookstore.datamodel.UserProfile;
import myy803.socialbookstore.formsdata.BookDto;
import myy803.socialbookstore.mappers.UserProfileMapper;

@Component
public abstract class AbstractRecommendationStrategy implements RecommendationsStrategy{
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	
	@Override
    public List<BookDto> recommend(String username) {
        UserProfile userProfile = userProfileMapper.findByUsername(username);
        List<BookDto> bookDtos = retrieveRecommendedBooks(userProfile);
        
        //extra block of code in order to not return the books the user himself provided or the ones he already requested
        List<BookDto> notTheseBooks = userProfile.buildBookOffersDtos();
        notTheseBooks.addAll(userProfile.buildBookRequestsDtos());
        return removeUneededBooks(bookDtos, notTheseBooks);
    }
    
    private List<BookDto> removeUneededBooks(List<BookDto> bookDtos, List<BookDto> notTheseBooks) {
        //extra function in order to not return the books the user himself provided or the ones he already requested
        List<Integer> notTheseBookIds = getUneededBookIds(notTheseBooks);
        
        List<Integer> seenIds = new ArrayList<>();
        Iterator<BookDto> iterator = bookDtos.iterator();
        while (iterator.hasNext()) {
            BookDto book = iterator.next();
            if (notTheseBookIds.contains(book.getId()) || seenIds.contains(book.getId())) {
                iterator.remove();
            } else {
                seenIds.add(book.getId());
            }
        }
        
        return bookDtos;
    }
    
    private List<Integer> getUneededBookIds(List<BookDto> notTheseBooks) {
        //extra function in order to not return the books the user himself provided or the ones he already requested
        List<Integer> notTheseBookIds = new ArrayList<>();
        for (BookDto book : notTheseBooks) {
            notTheseBookIds.add(book.getId());
        }
        
        return notTheseBookIds;
    }
	
	protected abstract List<BookDto> retrieveRecommendedBooks(UserProfile userProfile);
	
}
