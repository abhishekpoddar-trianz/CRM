package crm.controller;

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

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    private Export export;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        export = new Export(userService);
    }

    @Test
    void testConstructor() {
        assertNotNull(export);
    }

    @Test
    void testConstructorWithNullUserService() {
        assertDoesNotThrow(() -> new Export(null));
    }

    @Test
    void testDownload() {
        // Arrange
        List<Object> users = Arrays.asList(new Object(), new Object());
        when(userService.listAllUsers()).thenReturn(users);

        // Act
        String result = export.download(model);

        // Assert
        assertEquals("", result);
        verify(model).addAttribute("users", users);
        verify(userService).listAllUsers();
    }

    @Test
    void testDownload_EmptyUsersList() {
        // Arrange
        List<Object> emptyUsers = Arrays.asList();
        when(userService.listAllUsers()).thenReturn(emptyUsers);

        // Act
        String result = export.download(model);

        // Assert
        assertEquals("", result);
        verify(model).addAttribute("users", emptyUsers);
        verify(userService).listAllUsers();
    }

    @Test
    void testDownload_NullUsersList() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(null);

        // Act
        String result = export.download(model);

        // Assert
        assertEquals("", result);
        verify(model).addAttribute("users", null);
        verify(userService).listAllUsers();
    }

    @Test
    void testDownload_UserServiceInteraction() {
        // Arrange
        List<Object> users = Arrays.asList(new Object());
        when(userService.listAllUsers()).thenReturn(users);

        // Act
        export.download(model);

        // Assert
        verify(userService, times(1)).listAllUsers();
        verify(model, times(1)).addAttribute("users", users);
    }

    @Test
    void testDownload_ReturnValue() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        // Act
        String result = export.download(model);

        // Assert
        assertNotNull(result);
        assertEquals("", result);
    }

    @Test
    void testDownload_NoExceptions() {
        // Arrange
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        // Act & Assert
        assertDoesNotThrow(() -> export.download(model));
    }
}