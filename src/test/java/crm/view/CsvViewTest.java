package crm.view;

import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CsvViewTest {

    private CsvView csvView;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        csvView = new CsvView();
    }

    @Test
    void testConstructor() {
        assertNotNull(csvView);
        assertEquals("text/csv", csvView.getContentType());
    }

    @Test
    void testBuildCsvDocumentWithUsers() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        User user1 = User.builder()
                .id(1L)
                .username("user1")
                .email("user1@test.com")
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .enabled(1)
                .role(role)
                .build();

        List<User> users = Arrays.asList(user1);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        csvView.buildCsvDocument(model, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        verify(response).getWriter();
    }

    @Test
    void testBuildCsvDocumentSetsHeader() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("ROLE_ADMIN");

        User user = User.builder()
                .id(1L)
                .username("admin")
                .email("admin@test.com")
                .firstName("Admin")
                .lastName("User")
                .password("admin123")
                .enabled(1)
                .role(role)
                .build();

        List<User> users = Arrays.asList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);

        assertDoesNotThrow(() -> csvView.buildCsvDocument(model, request, response));
    }
}
