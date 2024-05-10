package sia.project;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sia.app.model.User;
import sia.app.repository.UserRepository;
import sia.app.service.UserService;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = UserService.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        reset(userRepository);
    }

    @Test
    public void testLoginUserSuccessfulAuthentication() {
        String login = "test";
        String password = "password";
        User expectedUser = new User(login, password, true);

        when(userRepository.findByLoginAndPassword(login, password)).thenReturn(expectedUser);

        User authenticatedUser = userService.loginUser(login, password);

        Assertions.assertEquals(expectedUser, authenticatedUser);
        verify(userRepository).findByLoginAndPassword(login, password);
    }

    @Test
    public void testLoginUserFailedAuthentication() {
        String login = "test";
        String password = "wrongPassword";

        when(userRepository.findByLoginAndPassword(login, password)).thenReturn(null);

        User authenticatedUser = userService.loginUser(login, password);

        Assertions.assertNull(authenticatedUser);
        verify(userRepository).findByLoginAndPassword(login, password);
    }
}
