package crm.controller;

import crm.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;

public class ExportTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    private Export exportController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        exportController = new Export(userService);
    }

    @Test
    public void testDownload() {
        when(userService.listAllUsers()).thenReturn(new ArrayList<>());

        String viewName = exportController.download(model);

        assertEquals("", viewName);
        verify(model, times(1)).addAttribute(eq("users"), any());
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    public void testDownloadWithUsers() {
        when(userService.listAllUsers()).thenReturn(new ArrayList<>());

        String result = exportController.download(model);

        assertNotNull(result);
        verify(userService, times(1)).listAllUsers();
    }

    @Test
    public void testExportControllerNotNull() {
        assertNotNull(exportController);
    }
}
