package crm.controller;

import crm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExportTest {

    private Export exportController;
    private UserService userService;
    private Model model;

    @BeforeEach
    public void setUp() {
        userService = mock(UserService.class);
        exportController = new Export(userService);
        model = mock(Model.class);
    }

    @Test
    public void testDownload() {
        when(userService.listAllUsers()).thenReturn(java.util.Collections.emptyList());

        String viewName = exportController.download(model);

        assertEquals("", viewName);
        verify(userService, times(1)).listAllUsers();
        verify(model, times(1)).addAttribute(eq("users"), any());
    }

    @Test
    public void testDownloadAddsUsersToModel() {
        exportController.download(model);

        verify(model, times(1)).addAttribute(eq("users"), any());
    }

    @Test
    public void testConstructor() {
        Export newExport = new Export(userService);
        assertNotNull(newExport);
    }
}
