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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registerController = new RegisterController(userService);
    }

    @Test
    void testConstructor() {
        assertNotNull(registerController);
    }

    @Test
    void testShowRegistrationPage() {
        User user = new User();

        String result = registerController.showRegistrationPage(model, user);

        assertEquals("register", result);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testShowRegistrationPage_NullUser() {
        User user = null;

        String result = registerController.showRegistrationPage(model, user);

        assertEquals("register", result);
        verify(model).addAttribute("user", null);
    }

    @Test
    void testProcessRegistrationForm_UserExists() {
        User user = new User();
        user.setUsername("existinguser");
        User existingUser = new User();
        when(userService.findByUsername("existinguser")).thenReturn(existingUser);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("register", result);
        verify(model).addAttribute("alreadyRegisteredMessage",
                "Oops!  There is already a user registered with the email provided.");
        verify(bindingResult).reject("email");
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    void testProcessRegistrationForm_UserNotExists_HasErrors() {
        User user = new User();
        user.setUsername("newuser");
        when(userService.findByUsername("newuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("redirect:/register", result);
        verify(userService, never()).saveUser(any(User.class));
    }

    @Test
    void testProcessRegistrationForm_UserNotExists_NoErrors() {
        User user = new User();
        user.setUsername("newuser");
        when(userService.findByUsername("newuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("success", result);
        verify(userService).saveUser(user);
    }

    @Test
    void testProcessRegistrationForm_NullUsername() {
        User user = new User();
        user.setUsername(null);
        when(userService.findByUsername(null)).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("success", result);
        verify(userService).saveUser(user);
    }

    @Test
    void testProcessRegistrationForm_EmptyUsername() {
        User user = new User();
        user.setUsername("");
        when(userService.findByUsername("")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("success", result);
        verify(userService).saveUser(user);
    }

    @Test
    void testProcessRegistrationForm_UserServiceCalled() {
        User user = new User();
        user.setUsername("testuser");
        when(userService.findByUsername("testuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        registerController.processRegistrationForm(model, user, bindingResult);

        verify(userService, times(1)).findByUsername("testuser");
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    void testProcessRegistrationForm_ModelInteractions() {
        User user = new User();
        user.setUsername("existinguser");
        User existingUser = new User();
        when(userService.findByUsername("existinguser")).thenReturn(existingUser);

        registerController.processRegistrationForm(model, user, bindingResult);

        verify(model, times(1)).addAttribute(eq("alreadyRegisteredMessage"), anyString());
    }

    @Test
    void testProcessRegistrationForm_BindingResultInteractions() {
        User user = new User();
        user.setUsername("existinguser");
        User existingUser = new User();
        when(userService.findByUsername("existinguser")).thenReturn(existingUser);

        registerController.processRegistrationForm(model, user, bindingResult);

        verify(bindingResult, times(1)).reject("email");
    }

    @Test
    void testRegistrationFlow_CompleteSuccess() {
        User user = new User();
        user.setUsername("newuser");
        when(userService.findByUsername("newuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("success", result);
        verify(userService).findByUsername("newuser");
        verify(userService).saveUser(user);
        verify(bindingResult, never()).reject(anyString());
        verify(model, never()).addAttribute(eq("alreadyRegisteredMessage"), anyString());
    }
}