package crm.controller;

import crm.entity.Role;
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
        assertNotNull(userController);
    }

    @Test
    void testShowAllUsers() {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User currentUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();

        List<User> users = Arrays.asList(currentUser);

        when(userDetails.getUsername()).thenReturn("testuser");
        when(userService.findByUsername("testuser")).thenReturn(currentUser);
        when(userService.listAllUsers()).thenReturn(users);

        String result = userController.showAllUsers(model, userDetails);

        assertEquals("user/list", result);
        verify(model).addAttribute("currentUser", currentUser);
        verify(model).addAttribute("users", users);
        verify(userService).findByUsername("testuser");
        verify(userService).listAllUsers();
    }

    @Test
    void testShowFormEditUser() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("edituser")
                .email("edit@example.com")
                .firstName("Edit")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .build();

        when(userService.showUser(userId)).thenReturn(user);

        String result = userController.showFormEditUser(model, userId);

        assertEquals("user/edit", result);
        verify(model).addAttribute("user", user);
        verify(userService).showUser(userId);
    }

    @Test
    void testProcessRequestEditUserWithErrors() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("edituser")
                .email("edit@example.com")
                .build();

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = userController.processRequestEditUser(userId, user, bindingResult);

        assertEquals("redirect:/user/edit/" + userId, result);
        verify(bindingResult).hasErrors();
        verify(userService, never()).editUser(any());
    }

    @Test
    void testProcessRequestEditUserWithoutErrors() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("edituser")
                .email("edit@example.com")
                .firstName("Edit")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .build();

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = userController.processRequestEditUser(userId, user, bindingResult);

        assertEquals("redirect:/user/list", result);
        verify(bindingResult).hasErrors();
        verify(userService).editUser(user);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        User user = User.builder()
                .id(userId)
                .username("deleteuser")
                .email("delete@example.com")
                .build();

        when(userService.showUser(userId)).thenReturn(user);

        String result = userController.deleteUser(userId);

        assertEquals("redirect:/user/list", result);
        verify(userService).showUser(userId);
        verify(userService).deleteUser(user);
    }

    @Test
    void testDeleteUserVerifiesServiceCalls() {
        Long userId = 2L;
        User user = User.builder()
                .id(userId)
                .username("user2")
                .email("user2@example.com")
                .build();

        when(userService.showUser(userId)).thenReturn(user);

        userController.deleteUser(userId);

        verify(userService, times(1)).showUser(userId);
        verify(userService, times(1)).deleteUser(user);
    }

    @Test
    void testShowAllUsersWithMultipleUsers() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("USER");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ADMIN");

        User currentUser = User.builder()
                .id(1L)
                .username("currentuser")
                .email("current@example.com")
                .firstName("Current")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .role(role1)
                .build();

        User otherUser = User.builder()
                .id(2L)
                .username("otheruser")
                .email("other@example.com")
                .firstName("Other")
                .lastName("User")
                .password("pass")
                .enabled(1)
                .role(role2)
                .build();

        List<User> users = Arrays.asList(currentUser, otherUser);

        when(userDetails.getUsername()).thenReturn("currentuser");
        when(userService.findByUsername("currentuser")).thenReturn(currentUser);
        when(userService.listAllUsers()).thenReturn(users);

        String result = userController.showAllUsers(model, userDetails);

        assertEquals("user/list", result);
        verify(model).addAttribute("currentUser", currentUser);
        verify(model).addAttribute("users", users);
    }
}
