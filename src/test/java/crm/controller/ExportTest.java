package crm.controller;

import crm.entity.Role;
import crm.entity.User;
import crm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExportTest {

    private Export exportController;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        exportController = new Export(userService);
    }

    @Test
    void testConstructor() {
        assertNotNull(exportController);
    }

    @Test
    void testDownload() {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User user1 = User.builder()
                .id(1L)
                .username("user1")
                .email("user1@example.com")
                .firstName("User")
                .lastName("One")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();

        User user2 = User.builder()
                .id(2L)
                .username("user2")
                .email("user2@example.com")
                .firstName("User")
                .lastName("Two")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();

        List<User> users = Arrays.asList(user1, user2);

        when(userService.listAllUsers()).thenReturn(users);

        String result = exportController.download(model);

        assertEquals("", result);
        verify(model).addAttribute("users", users);
        verify(userService).listAllUsers();
    }

    @Test
    void testDownloadReturnsEmptyString() {
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        String result = exportController.download(model);

        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    void testDownloadAddsUsersToModel() {
        List<User> users = Arrays.asList();
        when(userService.listAllUsers()).thenReturn(users);

        exportController.download(model);

        verify(model, times(1)).addAttribute("users", users);
    }

    @Test
    void testDownloadCallsUserService() {
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        exportController.download(model);

        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testDownloadWithMultipleUsers() {
        Role adminRole = new Role();
        adminRole.setId(2);
        adminRole.setName("ADMIN");

        User admin = User.builder()
                .id(3L)
                .username("admin")
                .email("admin@example.com")
                .firstName("Admin")
                .lastName("User")
                .password("adminpass")
                .enabled(1)
                .role(adminRole)
                .build();

        List<User> users = Arrays.asList(admin);
        when(userService.listAllUsers()).thenReturn(users);

        String result = exportController.download(model);

        assertEquals("", result);
        verify(model).addAttribute("users", users);
    }
}
