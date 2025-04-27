package myy803.socialbookstore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import myy803.socialbookstore.datamodel.User;
import myy803.socialbookstore.mappers.UserMapper;
import myy803.socialbookstore.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void saveUserTest() {
        User user = new User();
        user.setPassword("1234");
        user.setUsername("Chris");

        when(bCryptPasswordEncoder.encode("1234")).thenReturn("encodedPassword");

        userService.saveUser(user);

        verify(bCryptPasswordEncoder).encode("1234");
        verify(userMapper).save(user);
    }

    
    @Test
    void isUserPresentTest() {
        User user = new User();
        user.setUsername("Chris");

        when(userMapper.findByUsername("Chris")).thenReturn(Optional.of(user));

        boolean isPresent = userService.isUserPresent(user);
        assertTrue(isPresent);
    }
    
    
    @Test
    void findByIdTest() {
    	
        User user = new User();
        user.setUsername("Chris");

        when(userMapper.findByUsername("Chris")).thenReturn(Optional.of(user));

        User foundUser = userService.findById("Chris");

        assertEquals("Chris", foundUser.getUsername());
        verify(userMapper).findByUsername("Chris");
    }

    @Test
    void getUsernameTest() {
        String expectedUsername = "Chris";

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication auth = mock(Authentication.class);
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = mockStatic(SecurityContextHolder.class)) {
            mockedSecurityContextHolder.when(SecurityContextHolder::getContext).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(auth);
            when(auth.getName()).thenReturn(expectedUsername);

            UserServiceImpl userService = new UserServiceImpl();
            String actualUsername = userService.getUsername();

            assertEquals(expectedUsername, actualUsername);

            verify(securityContext).getAuthentication();
            verify(auth).getName();
        }
    }
    
}