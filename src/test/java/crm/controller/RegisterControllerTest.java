package crm.controller;

import crm.entity.Role;
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
    void testProcessRegistrationFormUserAlreadyExists() {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User user = User.builder()
                .username("existinguser")
                .email("existing@example.com")
                .firstName("Existing")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();

        User existingUser = User.builder()
                .id(1L)
                .username("existinguser")
                .email("existing@example.com")
                .build();

        when(userService.findByUsername("existinguser")).thenReturn(existingUser);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("register", result);
        verify(model).addAttribute(eq("alreadyRegisteredMessage"), anyString());
        verify(bindingResult).reject("email");
        verify(userService, never()).saveUser(any());
    }

    @Test
    void testProcessRegistrationFormWithBindingErrors() {
        User user = User.builder()
                .username("newuser")
                .email("new@example.com")
                .build();

        when(userService.findByUsername("newuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(true);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("redirect:/register", result);
        verify(userService, never()).saveUser(any());
    }

    @Test
    void testProcessRegistrationFormSuccess() {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User user = User.builder()
                .username("newuser")
                .email("new@example.com")
                .firstName("New")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();

        when(userService.findByUsername("newuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("success", result);
        verify(userService).saveUser(user);
    }

    @Test
    void testProcessRegistrationFormNullUserFromDB() {
        User user = User.builder()
                .username("uniqueuser")
                .email("unique@example.com")
                .firstName("Unique")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .build();

        when(userService.findByUsername("uniqueuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        String result = registerController.processRegistrationForm(model, user, bindingResult);

        assertEquals("success", result);
        verify(userService).findByUsername("uniqueuser");
        verify(userService).saveUser(user);
    }

    @Test
    void testProcessRegistrationFormVerifiesServiceCalls() {
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .build();

        when(userService.findByUsername("testuser")).thenReturn(null);
        when(bindingResult.hasErrors()).thenReturn(false);

        registerController.processRegistrationForm(model, user, bindingResult);

        verify(userService, times(1)).findByUsername("testuser");
        verify(userService, times(1)).saveUser(user);
    }
}
