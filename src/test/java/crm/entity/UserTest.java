package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Role testRole;

    @BeforeEach
    void setUp() {
        user = new User();
        testRole = new Role();
        testRole.setId(1);
        testRole.setName("ADMIN");
    }

    @Test
    void user_defaultConstructor_shouldCreateInstance() {
        // Act
        User newUser = new User();

        // Assert
        assertNotNull(newUser, "User should be created");
        assertNull(newUser.getId(), "Default id should be null");
        assertNull(newUser.getUsername(), "Default username should be null");
        assertNull(newUser.getEmail(), "Default email should be null");
        assertNull(newUser.getFirstName(), "Default firstName should be null");
        assertNull(newUser.getLastName(), "Default lastName should be null");
        assertNull(newUser.getPassword(), "Default password should be null");
        assertEquals(0, newUser.getEnabled(), "Default enabled should be 0");
        assertNull(newUser.getRole(), "Default role should be null");
    }

    @Test
    void user_allArgsConstructor_shouldCreateInstanceWithAllFields() {
        // Arrange
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";
        int enabled = 1;

        // Act
        User newUser = new User(id, username, email, firstName, lastName, password, enabled, testRole);

        // Assert
        assertNotNull(newUser, "User should be created");
        assertEquals(id, newUser.getId());
        assertEquals(username, newUser.getUsername());
        assertEquals(email, newUser.getEmail());
        assertEquals(firstName, newUser.getFirstName());
        assertEquals(lastName, newUser.getLastName());
        assertEquals(password, newUser.getPassword());
        assertEquals(enabled, newUser.getEnabled());
        assertEquals(testRole, newUser.getRole());
    }

    @Test
    void user_builder_shouldCreateInstanceWithSpecifiedFields() {
        // Arrange
        String username = "testuser";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";

        // Act
        User newUser = User.builder()
                .username(username)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .role(testRole)
                .build();

        // Assert
        assertNotNull(newUser, "User should be created");
        assertEquals(username, newUser.getUsername());
        assertEquals(email, newUser.getEmail());
        assertEquals(firstName, newUser.getFirstName());
        assertEquals(lastName, newUser.getLastName());
        assertEquals(testRole, newUser.getRole());
    }

    @Test
    void setId_withValidId_shouldSetId() {
        // Arrange
        Long expectedId = 123L;

        // Act
        user.setId(expectedId);

        // Assert
        assertEquals(expectedId, user.getId(), "Id should be set correctly");
    }

    @Test
    void setUsername_withValidUsername_shouldSetUsername() {
        // Arrange
        String expectedUsername = "testuser";

        // Act
        user.setUsername(expectedUsername);

        // Assert
        assertEquals(expectedUsername, user.getUsername(), "Username should be set correctly");
    }

    @Test
    void setEmail_withValidEmail_shouldSetEmail() {
        // Arrange
        String expectedEmail = "test@example.com";

        // Act
        user.setEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, user.getEmail(), "Email should be set correctly");
    }

    @Test
    void setFirstName_withValidFirstName_shouldSetFirstName() {
        // Arrange
        String expectedFirstName = "John";

        // Act
        user.setFirstName(expectedFirstName);

        // Assert
        assertEquals(expectedFirstName, user.getFirstName(), "FirstName should be set correctly");
    }

    @Test
    void setLastName_withValidLastName_shouldSetLastName() {
        // Arrange
        String expectedLastName = "Doe";

        // Act
        user.setLastName(expectedLastName);

        // Assert
        assertEquals(expectedLastName, user.getLastName(), "LastName should be set correctly");
    }

    @Test
    void setPassword_withValidPassword_shouldSetPassword() {
        // Arrange
        String expectedPassword = "password123";

        // Act
        user.setPassword(expectedPassword);

        // Assert
        assertEquals(expectedPassword, user.getPassword(), "Password should be set correctly");
    }

    @Test
    void setEnabled_withValidEnabled_shouldSetEnabled() {
        // Arrange
        int expectedEnabled = 1;

        // Act
        user.setEnabled(expectedEnabled);

        // Assert
        assertEquals(expectedEnabled, user.getEnabled(), "Enabled should be set correctly");
    }

    @Test
    void setRole_withValidRole_shouldSetRole() {
        // Act
        user.setRole(testRole);

        // Assert
        assertEquals(testRole, user.getRole(), "Role should be set correctly");
    }

    @Test
    void getColumnCount_shouldReturnFieldCount() {
        // Act
        int columnCount = user.getColumnCount();

        // Assert
        assertTrue(columnCount > 0, "Column count should be greater than 0");
        assertEquals(7, columnCount, "Should return number of declared fields");
    }

    @Test
    void getRole_id_withValidRole_shouldReturnRoleId() {
        // Arrange
        user.setRole(testRole);

        // Act
        int roleId = user.getRole_id();

        // Assert
        assertEquals(testRole.getId(), roleId, "Should return role id");
    }

    @Test
    void getRole_id_withNullRole_shouldThrowException() {
        // Arrange
        user.setRole(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> user.getRole_id(),
                "Should throw NullPointerException when role is null");
    }

    @Test
    void getRole_name_withValidRole_shouldReturnRoleName() {
        // Arrange
        user.setRole(testRole);

        // Act
        String roleName = user.getRole_name();

        // Assert
        assertEquals(testRole.getName(), roleName, "Should return role name");
    }

    @Test
    void getRole_name_withNullRole_shouldThrowException() {
        // Arrange
        user.setRole(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> user.getRole_name(),
                "Should throw NullPointerException when role is null");
    }

    @Test
    void getName_withBothNamesSet_shouldReturnFullName() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        user.setFirstName(firstName);
        user.setLastName(lastName);

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("John Doe", fullName, "Should return concatenated first and last name");
    }

    @Test
    void getName_withNullFirstName_shouldHandleGracefully() {
        // Arrange
        user.setFirstName(null);
        user.setLastName("Doe");

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("null Doe", fullName, "Should handle null first name");
    }

    @Test
    void getName_withNullLastName_shouldHandleGracefully() {
        // Arrange
        user.setFirstName("John");
        user.setLastName(null);

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("John null", fullName, "Should handle null last name");
    }

    @Test
    void getName_withBothNamesNull_shouldHandleGracefully() {
        // Arrange
        user.setFirstName(null);
        user.setLastName(null);

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("null null", fullName, "Should handle both names being null");
    }

    @Test
    void equals_withSameValues_shouldBeEqual() {
        // Arrange
        User user1 = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        User user2 = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .build();

        // Act & Assert
        assertEquals(user1, user2, "Users with same values should be equal");
        assertEquals(user1.hashCode(), user2.hashCode(), "Hash codes should be equal");
    }

    @Test
    void toString_shouldReturnStringRepresentation() {
        // Arrange
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        // Act
        String result = user.toString();

        // Assert
        assertNotNull(result, "toString should not return null");
        assertTrue(result.contains("1"), "toString should contain id");
        assertTrue(result.contains("testuser"), "toString should contain username");
        assertTrue(result.contains("test@example.com"), "toString should contain email");
    }

    @Test
    void userClass_shouldHaveCorrectJPAAnnotations() {
        // Assert
        assertTrue(User.class.isAnnotationPresent(Entity.class), "User should have @Entity annotation");

        Entity entityAnnotation = User.class.getAnnotation(Entity.class);
        assertEquals("users", entityAnnotation.name(), "Entity name should be 'users'");
    }

    @Test
    void idField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var idField = User.class.getDeclaredField("id");

        // Assert
        assertTrue(idField.isAnnotationPresent(Id.class), "id field should have @Id annotation");
        assertTrue(idField.isAnnotationPresent(GeneratedValue.class), "id field should have @GeneratedValue annotation");

        GeneratedValue generatedValue = idField.getAnnotation(GeneratedValue.class);
        assertEquals(GenerationType.IDENTITY, generatedValue.strategy(), "GeneratedValue strategy should be IDENTITY");
    }

    @Test
    void usernameField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var usernameField = User.class.getDeclaredField("username");

        // Assert
        assertTrue(usernameField.isAnnotationPresent(Column.class), "username field should have @Column annotation");

        Column column = usernameField.getAnnotation(Column.class);
        assertFalse(column.nullable(), "Column should not be nullable");
        assertTrue(column.unique(), "Column should be unique");
    }

    @Test
    void emailField_shouldHaveCorrectValidationAnnotations() throws NoSuchFieldException {
        // Arrange
        var emailField = User.class.getDeclaredField("email");

        // Assert
        assertTrue(emailField.isAnnotationPresent(Column.class), "email field should have @Column annotation");
        assertTrue(emailField.isAnnotationPresent(Email.class), "email field should have @Email annotation");
        assertTrue(emailField.isAnnotationPresent(NotEmpty.class), "email field should have @NotEmpty annotation");

        Column column = emailField.getAnnotation(Column.class);
        assertEquals("email", column.name(), "Column name should be 'email'");
        assertFalse(column.nullable(), "Column should not be nullable");
        assertTrue(column.unique(), "Column should be unique");
    }

    @Test
    void roleField_shouldHaveCorrectJPAAnnotations() throws NoSuchFieldException {
        // Arrange
        var roleField = User.class.getDeclaredField("role");

        // Assert
        assertTrue(roleField.isAnnotationPresent(ManyToOne.class), "role field should have @ManyToOne annotation");
    }

    @Test
    void userClass_shouldHaveLombokAnnotations() {
        // Assert
        assertTrue(User.class.isAnnotationPresent(lombok.Data.class), "User should have @Data annotation");
        assertTrue(User.class.isAnnotationPresent(lombok.Builder.class), "User should have @Builder annotation");
        assertTrue(User.class.isAnnotationPresent(lombok.NoArgsConstructor.class), "User should have @NoArgsConstructor annotation");
        assertTrue(User.class.isAnnotationPresent(lombok.AllArgsConstructor.class), "User should have @AllArgsConstructor annotation");
    }
}