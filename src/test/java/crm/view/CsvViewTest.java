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
    void testBuildCsvDocumentWithValidUsers() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setUsername("johndoe");
        user1.setEmail("john@example.com");
        user1.setPassword("password123");
        user1.setEnabled(1);
        user1.setRole(role);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Smith");
        user2.setUsername("janesmith");
        user2.setEmail("jane@example.com");
        user2.setPassword("password456");
        user2.setEnabled(1);
        user2.setRole(role);

        List<User> users = Arrays.asList(user1, user2);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        csvView.buildCsvDocument(model, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        String csvContent = stringWriter.toString();
        assertTrue(csvContent.contains("FirstName"));
        assertTrue(csvContent.contains("LastName"));
        assertTrue(csvContent.contains("Username"));
    }

    @Test
    void testBuildCsvDocumentSetsContentDisposition() throws Exception {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("testpass");
        user.setEnabled(1);
        user.setRole(role);

        List<User> users = Arrays.asList(user);
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        csvView.buildCsvDocument(model, request, response);

        verify(response, times(1)).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
    }

    @Test
    void testBuildCsvDocumentWithEmptyUsersList() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("users", Arrays.asList());

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        csvView.buildCsvDocument(model, request, response);

        String csvContent = stringWriter.toString();
        assertTrue(csvContent.contains("FirstName"));
    }

    @Test
    void testConstructorSetsContentType() {
        CsvView view = new CsvView();
        assertNotNull(view);
        assertEquals("text/csv", view.getContentType());
    }
}
