package crm.controller;

import crm.entity.User;
import crm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    private RegisterController registerController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerController = new RegisterController(userService);
    }

    @Test
    void testConstructor() {
        RegisterController controller = new RegisterController(userService);
        assertNotNull(controller);
    }

    @Test
    void testShowRegistrationPage() {
        User user = new User();

        String viewName = registerController.showRegistrationPage(model, user);

        assertEquals("register", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testProcessRegistrationFormWithNewUser() {
        User user = new User();
        user.setUsername("newuser");

        when(userService.findByUsername("newuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("success", viewName);
        verify(userService).saveUser(user);
    }

    @Test
    void testProcessRegistrationFormWithExistingUser() {
        User user = new User();
        user.setUsername("existinguser");

        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userService.findByUsername("existinguser")).thenReturn(existingUser);

        String viewName = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("register", viewName);
        verify(model).addAttribute(eq("alreadyRegisteredMessage"), anyString());
        verify(bindingResult).reject("email");
        verify(userService, never()).saveUser(user);
    }

    @Test
    void testProcessRegistrationFormWithErrors() {
        User user = new User();
        user.setUsername("testuser");

        when(userService.findByUsername("testuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("redirect:/register", viewName);
        verify(userService, never()).saveUser(user);
    }

    @Test
    void testProcessRegistrationFormValidatesUsername() {
        User user = new User();
        user.setUsername("testuser");

        when(userService.findByUsername("testuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        registerController.processRegistrationForm(model, user, bindingResult);

        verify(userService, times(1)).findByUsername("testuser");
    }
}
