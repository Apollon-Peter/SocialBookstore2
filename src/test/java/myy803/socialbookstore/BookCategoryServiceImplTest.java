package myy803.socialbookstore;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import myy803.socialbookstore.datamodel.BookCategory;
import myy803.socialbookstore.mappers.BookCategoryMapper;
import myy803.socialbookstore.services.BookCategoryServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCategoryServiceImplTest {

    @Mock
    private BookCategoryMapper bookCategoryMapper;
    
    @InjectMocks
    private BookCategoryServiceImpl bookCategoryService;

    
    @Test
    void getAllBookCategoriesTest() {
        
        BookCategory category1 = new BookCategory();
        category1.setCategoryId(1);
        category1.setName("Fiction");

        BookCategory category2 = new BookCategory();
        category2.setCategoryId(2);
        category2.setName("Science");

        BookCategory category3 = new BookCategory();
        category3.setCategoryId(3);
        category3.setName("History");

        List<BookCategory> mockCategories = Arrays.asList(category1, category2, category3);

        when(bookCategoryMapper.findAll()).thenReturn(mockCategories);

        
        List<BookCategory> result = bookCategoryService.getAllBookCategories();

        
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Fiction", result.get(0).getName());
        assertEquals("Science", result.get(1).getName());
        assertEquals("History", result.get(2).getName());

        
        verify(bookCategoryMapper).findAll();
    }

}
