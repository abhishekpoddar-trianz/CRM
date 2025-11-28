package crm.controller;

import crm.entity.User;
import crm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExportTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private Export export;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testDownload() {
        User user = new User();
        when(userService.listAllUsers()).thenReturn(Arrays.asList(user));

        String viewName = export.download(model);

        assertEquals("", viewName);
        verify(model).addAttribute(eq("users"), any());
        verify(userService).listAllUsers();
    }

    @Test
    void testDownloadReturnsEmptyString() {
        when(userService.listAllUsers()).thenReturn(Arrays.asList());

        String viewName = export.download(model);

        assertNotNull(viewName);
        assertEquals("", viewName);
    }
}
