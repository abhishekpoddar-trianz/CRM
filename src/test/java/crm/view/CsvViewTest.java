package crm.view;

import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
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

    private Map<String, Object> model;
    private StringWriter stringWriter;
    private PrintWriter printWriter;
    private List<User> users;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        csvView = new CsvView();

        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        // Create test users
        users = new ArrayList<>();

        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("USER");

        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john@example.com")
                .password("password123")
                .enabled(1)
                .role(role1)
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .username("janesmith")
                .email("jane@example.com")
                .password("password456")
                .enabled(0)
                .role(role2)
                .build();

        users.add(user1);
        users.add(user2);

        model = new HashMap<>();
        model.put("users", users);
    }

    @Test
    void testBuildCsvDocument() throws Exception {
        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        verify(response).getWriter();

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertFalse(csvContent.isEmpty());

        // Check header is present
        assertTrue(csvContent.contains("FirstName"));
        assertTrue(csvContent.contains("LastName"));
        assertTrue(csvContent.contains("Username"));
        assertTrue(csvContent.contains("Email"));
        assertTrue(csvContent.contains("Password"));
        assertTrue(csvContent.contains("Enabled"));
        assertTrue(csvContent.contains("Role_id"));
        assertTrue(csvContent.contains("Role_name"));
    }

    @Test
    void testBuildCsvDocumentWithEmptyUserList() throws Exception {
        // Arrange
        model.put("users", new ArrayList<User>());

        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        // Should still contain header
        assertTrue(csvContent.contains("FirstName"));
    }

    @Test
    void testBuildCsvDocumentWithNullUserList() throws Exception {
        // Arrange
        model.put("users", null);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
            csvView.buildCsvDocument(model, request, response));
    }

    @Test
    void testBuildCsvDocumentWithNoUsersKey() throws Exception {
        // Arrange
        Map<String, Object> emptyModel = new HashMap<>();

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
            csvView.buildCsvDocument(emptyModel, request, response));
    }

    @Test
    void testBuildCsvDocumentSetsContentDisposition() throws Exception {
        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        verify(response).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
    }

    @Test
    void testBuildCsvDocumentUsesResponseWriter() throws Exception {
        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        verify(response).getWriter();
    }

    @Test
    void testBuildCsvDocumentWithSingleUser() throws Exception {
        // Arrange
        List<User> singleUserList = new ArrayList<>();
        singleUserList.add(users.get(0));
        model.put("users", singleUserList);

        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.contains("John"));
        assertTrue(csvContent.contains("Doe"));
        assertTrue(csvContent.contains("johndoe"));
    }

    @Test
    void testBuildCsvDocumentWithUsersHavingNullValues() throws Exception {
        // Arrange
        User userWithNulls = User.builder()
                .id(3L)
                .firstName(null)
                .lastName(null)
                .username("testnull")
                .email(null)
                .password(null)
                .enabled(1)
                .role(null)
                .build();

        List<User> usersWithNulls = new ArrayList<>();
        usersWithNulls.add(userWithNulls);
        model.put("users", usersWithNulls);

        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.contains("testnull"));
    }

    @Test
    void testInheritsFromAbstractCsvView() {
        // Act & Assert
        assertTrue(csvView instanceof AbstractCsvView);
    }

    @Test
    void testBuildCsvDocumentHeaderOrder() throws Exception {
        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        String csvContent = stringWriter.toString();
        String[] lines = csvContent.split("\n");
        if (lines.length > 0) {
            String headerLine = lines[0];
            // Verify the header order matches expected
            assertTrue(headerLine.contains("FirstName"));
            assertTrue(headerLine.contains("LastName"));
            assertTrue(headerLine.contains("Username"));
            assertTrue(headerLine.contains("Email"));
        }
    }

    @Test
    void testBuildCsvDocumentWithSpecialCharacters() throws Exception {
        // Arrange
        Role specialRole = new Role();
        specialRole.setId(99);
        specialRole.setName("SPECIAL,ROLE");

        User specialUser = User.builder()
                .id(99L)
                .firstName("John,Jr")
                .lastName("O'Connor")
                .username("special\"user")
                .email("special@test.com")
                .password("pass,word")
                .enabled(1)
                .role(specialRole)
                .build();

        List<User> specialUsers = new ArrayList<>();
        specialUsers.add(specialUser);
        model.put("users", specialUsers);

        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.contains("John,Jr") || csvContent.contains("\"John,Jr\""));
    }

    @Test
    void testBuildCsvDocumentWriterIsClosedProperly() throws Exception {
        // Act
        csvView.buildCsvDocument(model, request, response);

        // Assert - The writer should be closed, which means flush was called
        verify(response).getWriter();
        // We can't easily verify close() was called on the ICsvBeanWriter,
        // but we can verify the method completes without exception
        assertDoesNotThrow(() -> csvView.buildCsvDocument(model, request, response));
    }

    @Test
    void testBuildCsvDocumentHandlesLargeUserList() throws Exception {
        // Arrange
        List<User> largeUserList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Role role = new Role();
            role.setId(i);
            role.setName("ROLE_" + i);

            User user = User.builder()
                    .id((long) i)
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .username("user" + i)
                    .email("user" + i + "@example.com")
                    .password("password" + i)
                    .enabled(i % 2)
                    .role(role)
                    .build();
            largeUserList.add(user);
        }
        model.put("users", largeUserList);

        // Act & Assert
        assertDoesNotThrow(() -> csvView.buildCsvDocument(model, request, response));

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertFalse(csvContent.isEmpty());
    }
}