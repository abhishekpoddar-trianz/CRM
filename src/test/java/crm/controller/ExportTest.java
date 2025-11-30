package crm.controller;

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

    private Export export;

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        export = new Export(userService);
    }

    @Test
    void testConstructor() {
        Export export = new Export(userService);
        assertNotNull(export);
    }

    @Test
    void testConstructorWithNullService() {
        Export export = new Export(null);
        assertNotNull(export);
    }

    @Test
    void testDownload() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        List<User> users = Arrays.asList(user1, user2);

        when(userService.listAllUsers()).thenReturn(users);

        String viewName = export.download(model);

        assertEquals("", viewName);
        verify(model).addAttribute("users", users);
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testDownloadWithEmptyUsersList() {
        List<User> emptyList = Arrays.asList();

        when(userService.listAllUsers()).thenReturn(emptyList);

        String viewName = export.download(model);

        assertEquals("", viewName);
        verify(model).addAttribute("users", emptyList);
    }

    @Test
    void testDownloadReturnsEmptyString() {
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        String result = export.download(model);

        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    void testDownloadCallsUserService() {
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        export.download(model);

        verify(userService, times(1)).listAllUsers();
    }

    @Test
    void testDownloadAddsUsersToModel() {
        List<User> users = Arrays.asList(new User());

        when(userService.listAllUsers()).thenReturn(users);

        export.download(model);

        verify(model, times(1)).addAttribute("users", users);
    }
}
