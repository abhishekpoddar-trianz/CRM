package crm.controller;

import crm.entity.User;
import crm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @Mock
    private UserDetails userDetails;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService);
    }

    @Test
    void testConstructor() {
        UserController controller = new UserController(userService);
        assertNotNull(controller);
    }

    @Test
    void testShowAllUsers() {
        User user = new User();
        user.setUsername("testuser");

        List<User> users = Arrays.asList(user);

        when(userDetails.getUsername()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(user);
        when(userService.listAllUsers()).thenReturn(users);

        String viewName = userController.showAllUsers(model, userDetails);

        assertEquals("user/list", viewName);
        verify(model).addAttribute("currentUser", user);
        verify(model).addAttribute("users", users);
    }

    @Test
    void testShowFormEditUser() {
        User user = new User();
        user.setId(1L);

        when(userService.showUser(1L)).thenReturn(user);

        String viewName = userController.showFormEditUser(model, 1L);

        assertEquals("user/edit", viewName);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testProcessRequestEditUserWithValidData() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = userController.processRequestEditUser(1L, user, bindingResult);

        assertEquals("redirect:/user/list", viewName);
        verify(userService).editUser(user);
    }

    @Test
    void testProcessRequestEditUserWithErrors() {
        User user = new User();
        user.setId(1L);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = userController.processRequestEditUser(1L, user, bindingResult);

        assertEquals("redirect:/user/edit/1", viewName);
        verify(userService, never()).editUser(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User();
        user.setId(1L);

        when(userService.showUser(1L)).thenReturn(user);

        String viewName = userController.deleteUser(1L);

        assertEquals("redirect:/user/list", viewName);
        verify(userService).deleteUser(user);
    }

    @Test
    void testShowAllUsersWithNullCurrentUser() {
        when(userDetails.getUsername()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(null);
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        String viewName = userController.showAllUsers(model, userDetails);

        assertEquals("user/list", viewName);
        verify(model).addAttribute("currentUser", null);
    }

    @Test
    void testShowFormEditUserWithInvalidId() {
        when(userService.showUser(999L)).thenReturn(null);

        String viewName = userController.showFormEditUser(model, 999L);

        assertEquals("user/edit", viewName);
        verify(model).addAttribute("user", null);
    }

    @Test
    void testDeleteUserWithValidId() {
        User user = new User();
        user.setId(5L);
        user.setUsername("deleteuser");

        when(userService.showUser(5L)).thenReturn(user);

        String viewName = userController.deleteUser(5L);

        assertEquals("redirect:/user/list", viewName);
        verify(userService, times(1)).deleteUser(user);
    }
}
