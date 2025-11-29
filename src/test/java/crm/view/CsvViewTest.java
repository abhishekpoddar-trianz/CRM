package crm.view;

import crm.entity.User;
import crm.entity.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class CsvViewTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter printWriter;

    private CsvView csvView;
    private StringWriter stringWriter;

    @BeforeEach
    public void setUp() throws Exception {
        csvView = new CsvView();
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

    @Test
    public void testBuildCsvDocumentWithUsers() throws Exception {
        List<User> users = createTestUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        csvView.buildCsvDocument(model, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        verify(response).getWriter();

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.contains("FirstName"));
        assertTrue(csvContent.contains("LastName"));
        assertTrue(csvContent.contains("Username"));
        assertTrue(csvContent.contains("Email"));
    }

    @Test
    public void testBuildCsvDocumentWithEmptyUsers() throws Exception {
        List<User> users = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        csvView.buildCsvDocument(model, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        verify(response).getWriter();

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.contains("FirstName")); // Header should still be present
    }

    @Test
    public void testBuildCsvDocumentWithNullUsers() throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("users", null);

        assertThrows(Exception.class, () -> {
            csvView.buildCsvDocument(model, request, response);
        });
    }

    @Test
    public void testBuildCsvDocumentSetsCorrectHeader() throws Exception {
        List<User> users = new ArrayList<>();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        csvView.buildCsvDocument(model, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
    }

    @Test
    public void testBuildCsvDocumentWithMultipleUsers() throws Exception {
        List<User> users = createTestUsers();
        users.add(createTestUser("Jane", "Smith", "janesmith", "jane@example.com"));
        users.add(createTestUser("Bob", "Johnson", "bobjohnson", "bob@example.com"));

        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        csvView.buildCsvDocument(model, request, response);

        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");

        String csvContent = stringWriter.toString();
        assertTrue(csvContent.contains("John"));
        assertTrue(csvContent.contains("Jane"));
        assertTrue(csvContent.contains("Bob"));
    }

    @Test
    public void testInheritanceStructure() {
        assertTrue(csvView instanceof AbstractCsvView);
    }

    @Test
    public void testCsvHeaderOrder() throws Exception {
        List<User> users = createTestUsers();
        Map<String, Object> model = new HashMap<>();
        model.put("users", users);

        csvView.buildCsvDocument(model, request, response);

        String csvContent = stringWriter.toString();
        String[] lines = csvContent.split("\n");
        if (lines.length > 0) {
            String headerLine = lines[0];
            assertTrue(headerLine.contains("FirstName"));
            assertTrue(headerLine.contains("LastName"));
            assertTrue(headerLine.contains("Username"));
            assertTrue(headerLine.contains("Email"));
        }
    }

    @Test
    public void testBuildCsvDocumentWithNullModel() throws Exception {
        assertThrows(Exception.class, () -> {
            csvView.buildCsvDocument(null, request, response);
        });
    }

    @Test
    public void testBuildCsvDocumentWithEmptyModel() throws Exception {
        Map<String, Object> model = new HashMap<>();

        assertThrows(Exception.class, () -> {
            csvView.buildCsvDocument(model, request, response);
        });
    }

    private List<User> createTestUsers() {
        List<User> users = new ArrayList<>();
        users.add(createTestUser("John", "Doe", "johndoe", "john@example.com"));
        return users;
    }

    private User createTestUser(String firstName, String lastName, String username, String email) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword("password123");
        user.setEnabled(1);

        Role role = new Role();
        role.setId(1);
        role.setName("USER");
        user.setRole(role);

        return user;
    }
}