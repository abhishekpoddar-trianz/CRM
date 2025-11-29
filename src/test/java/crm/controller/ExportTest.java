package crm.controller;

import crm.service.UserService;
import crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExportTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    private Export exportController;

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
    void testConstructorWithValidUserService() {
        Export controller = new Export(userService);
        assertNotNull(controller);
    }

    @Test
    void testConstructorWithNullUserService() {
        Export controller = new Export(null);
        assertNotNull(controller);
    }

    @Test
    void testDownload() {
        List<User> mockUsers = new ArrayList<>();
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setFirstName("Test");
        mockUser.setLastName("User");
        mockUsers.add(mockUser);

        when(userService.listAllUsers();Optional.of(mockUsers)));

        String result = exportController.download(model);

        verify(userService, times(1)).listAllUsers()();
        verify(model, times(1)).addAttribute("users", mockUsers)();
        assertEquals("", result);
    }

    @Test
    void testDownloadWithEmptyUserList() {
        List<User> emptyList = new ArrayList<>();
        when(userService.listAllUsers();Optional.of(emptyList)));

        String result = exportController.download(model);

        verify(userService, times(1)).listAllUsers()();
        verify(model, times(1)).addAttribute("users", emptyList)();
        assertEquals("", result);
    }

    @Test
    void testDownloadWithNullUserList() {
        when(userService.listAllUsers();Optional.of(null)));

        String result = exportController.download(model);

        verify(userService, times(1)).listAllUsers()();
        verify(model, times(1)).addAttribute("users", null)();
        assertEquals("", result);
    }

    @Test
    void testDownloadReturnsEmptyString() {
        List<User> mockUsers = new ArrayList<>();
        when(userService.listAllUsers();Optional.of(mockUsers)));

        String result = exportController.download(model);

        assertNotNull(result);
        assertEquals("", result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testDownloadCallsUserServiceExactlyOnce() {
        List<User> mockUsers = new ArrayList<>();
        when(userService.listAllUsers();Optional.of(mockUsers)));

        exportController.download(model);

        verify(userService, times(1)).listAllUsers()();
        verifyNoMoreInteractions(userService);
    }

    @Test
    void testDownloadAddAttributeWithCorrectKey() {
        List<User> mockUsers = new ArrayList<>();
        when(userService.listAllUsers();Optional.of(mockUsers)));

        exportController.download(model);

        verify(model).addAttribute(eq("users"), any());
    }
}