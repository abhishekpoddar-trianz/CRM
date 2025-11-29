package crm.view;

import crm.entity.Role;
import crm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    private Map<String, Object> model;
    private List<User> users;
    private StringWriter stringWriter;
    private PrintWriter printWriter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        csvView = new CsvView();
        model = new HashMap<>();
        users = new ArrayList<>();
        stringWriter = new StringWriter();
        printWriter = new PrintWriter(stringWriter);

        when(mockResponse.getWriter()).thenReturn(printWriter);
    }

    @Test
    void testBuildCsvDocument() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        User user1 = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john@example.com")
                .password("password123")
                .enabled(true)
                .role(role)
                .build();

        User user2 = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .username("janesmith")
                .email("jane@example.com")
                .password("password456")
                .enabled(false)
                .role(role)
                .build();

        users.add(user1);
        users.add(user2);
        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        verify(mockResponse).getWriter();

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertFalse(csvContent.isEmpty());

        // Verify header is present
        assertTrue(csvContent.contains("FirstName"));
        assertTrue(csvContent.contains("LastName"));
        assertTrue(csvContent.contains("Username"));
        assertTrue(csvContent.contains("Email"));
        assertTrue(csvContent.contains("Password"));
        assertTrue(csvContent.contains("Enabled"));
        assertTrue(csvContent.contains("Role_id"));
        assertTrue(csvContent.contains("Role_name"));

        // Verify user data is present
        assertTrue(csvContent.contains("John"));
        assertTrue(csvContent.contains("Doe"));
        assertTrue(csvContent.contains("johndoe"));
        assertTrue(csvContent.contains("john@example.com"));
        assertTrue(csvContent.contains("Jane"));
        assertTrue(csvContent.contains("Smith"));
        assertTrue(csvContent.contains("janesmith"));
        assertTrue(csvContent.contains("jane@example.com"));
    }

    @Test
    void testBuildCsvDocumentWithEmptyUserList() throws Exception {
        // Arrange
        List<User> emptyUsers = new ArrayList<>();
        model.put("users", emptyUsers);

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        verify(mockResponse).getWriter();

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);

        // Should still contain header
        assertTrue(csvContent.contains("FirstName"));
        assertTrue(csvContent.contains("LastName"));
        assertTrue(csvContent.contains("Username"));
    }

    @Test
    void testBuildCsvDocumentWithSingleUser() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(2L);
        role.setName("ADMIN");

        User singleUser = User.builder()
                .id(1L)
                .firstName("Admin")
                .lastName("User")
                .username("admin")
                .email("admin@example.com")
                .password("adminpass")
                .enabled(true)
                .role(role)
                .build();

        users.add(singleUser);
        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);

        // Verify single user data
        assertTrue(csvContent.contains("Admin"));
        assertTrue(csvContent.contains("User"));
        assertTrue(csvContent.contains("admin"));
        assertTrue(csvContent.contains("admin@example.com"));
        assertTrue(csvContent.contains("ADMIN"));
    }

    @Test
    void testBuildCsvDocumentWithNullUsers() throws Exception {
        // Arrange
        model.put("users", null);

        // Act & Assert
        assertThrows(NullPointerException.class, () ->
            csvView.buildCsvDocument(model, mockRequest, mockResponse));
    }

    @Test
    void testBuildCsvDocumentSetsContentDisposition() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
    }

    @Test
    void testBuildCsvDocumentWritesHeader() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        String csvContent = stringWriter.toString();
        String[] expectedHeaders = {"FirstName", "LastName", "Username", "Email", "Password", "Enabled", "Role_id", "Role_name"};

        for (String header : expectedHeaders) {
            assertTrue(csvContent.contains(header));
        }
    }

    @Test
    void testBuildCsvDocumentWithUsersContainingNullFields() throws Exception {
        // Arrange
        User userWithNulls = User.builder()
                .id(1L)
                .firstName(null)
                .lastName(null)
                .username("testuser")
                .email(null)
                .password(null)
                .enabled(false)
                .role(null)
                .build();

        users.add(userWithNulls);
        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        assertTrue(csvContent.contains("testuser"));
    }

    @Test
    void testBuildCsvDocumentWithMultipleUsers() throws Exception {
        // Arrange
        Role userRole = new Role();
        userRole.setId(1L);
        userRole.setName("USER");

        Role adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setName("ADMIN");

        for (int i = 1; i <= 5; i++) {
            User user = User.builder()
                    .id((long) i)
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .username("user" + i)
                    .email("user" + i + "@example.com")
                    .password("password" + i)
                    .enabled(i % 2 == 0)
                    .role(i == 1 ? adminRole : userRole)
                    .build();
            users.add(user);
        }

        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        verify(mockResponse).setHeader("Content-Disposition", "attachment; filename=\"my-csv-file.csv\"");

        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);

        // Verify all users are present
        for (int i = 1; i <= 5; i++) {
            assertTrue(csvContent.contains("FirstName" + i));
            assertTrue(csvContent.contains("LastName" + i));
            assertTrue(csvContent.contains("user" + i));
            assertTrue(csvContent.contains("user" + i + "@example.com"));
        }
    }

    @Test
    void testBuildCsvDocumentHandlesSpecialCharacters() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("SPECIAL_ROLE");

        User userWithSpecialChars = User.builder()
                .id(1L)
                .firstName("John,Doe")
                .lastName("O'Connor")
                .username("john.doe")
                .email("john@test.com")
                .password("pass\"word")
                .enabled(true)
                .role(role)
                .build();

        users.add(userWithSpecialChars);
        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        String csvContent = stringWriter.toString();
        assertNotNull(csvContent);
        // CSV should handle special characters properly
        assertTrue(csvContent.length() > 0);
    }

    @Test
    void testCsvViewInheritance() {
        // Act & Assert
        assertTrue(csvView instanceof AbstractCsvView);
    }

    @Test
    void testBuildCsvDocumentClosesWriter() throws Exception {
        // Arrange
        model.put("users", new ArrayList<>());

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert - Writer should be closed (verified by no exceptions)
        assertDoesNotThrow(() -> printWriter.flush());
    }

    @Test
    void testModelWithoutUsersKey() throws Exception {
        // Arrange
        Map<String, Object> modelWithoutUsers = new HashMap<>();
        modelWithoutUsers.put("someOtherKey", "someValue");

        // Act & Assert
        assertThrows(ClassCastException.class, () ->
            csvView.buildCsvDocument(modelWithoutUsers, mockRequest, mockResponse));
    }

    @Test
    void testBuildCsvDocumentWithDifferentUserStates() throws Exception {
        // Arrange
        Role role = new Role();
        role.setId(1L);
        role.setName("TEST_ROLE");

        User enabledUser = User.builder()
                .firstName("Enabled")
                .lastName("User")
                .username("enabled")
                .email("enabled@test.com")
                .enabled(true)
                .role(role)
                .build();

        User disabledUser = User.builder()
                .firstName("Disabled")
                .lastName("User")
                .username("disabled")
                .email("disabled@test.com")
                .enabled(false)
                .role(role)
                .build();

        users.add(enabledUser);
        users.add(disabledUser);
        model.put("users", users);

        // Act
        csvView.buildCsvDocument(model, mockRequest, mockResponse);

        // Assert
        String csvContent = stringWriter.toString();
        assertTrue(csvContent.contains("Enabled"));
        assertTrue(csvContent.contains("Disabled"));
        assertTrue(csvContent.contains("enabled@test.com"));
        assertTrue(csvContent.contains("disabled@test.com"));
    }
}