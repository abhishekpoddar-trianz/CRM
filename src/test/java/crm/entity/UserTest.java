package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Validator validator;
    private Role testRole;

    @BeforeEach
    void setUp() {
        user = new User();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        testRole = new Role();
        testRole.setId(1L);
        testRole.setName("USER");
    }

    @Test
    void testDefaultConstructor() {
        // Act
        User newUser = new User();

        // Assert
        assertNotNull(newUser);
        assertNull(newUser.getId());
        assertNull(newUser.getUsername());
        assertNull(newUser.getEmail());
        assertNull(newUser.getFirstName());
        assertNull(newUser.getLastName());
        assertNull(newUser.getPassword());
        assertFalse(newUser.isEnabled());
        assertNull(newUser.getRole());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String username = "testuser";
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String password = "password123";
        boolean enabled = true;

        // Act
        User newUser = new User(id, username, email, firstName, lastName, password, enabled, testRole);

        // Assert
        assertEquals(id, newUser.getId());
        assertEquals(username, newUser.getUsername());
        assertEquals(email, newUser.getEmail());
        assertEquals(firstName, newUser.getFirstName());
        assertEquals(lastName, newUser.getLastName());
        assertEquals(password, newUser.getPassword());
        assertEquals(enabled, newUser.isEnabled());
        assertEquals(testRole, newUser.getRole());
    }

    @Test
    void testBuilderPattern() {
        // Act
        User builtUser = User.builder()
                .id(1L)
                .username("builderuser")
                .email("builder@test.com")
                .firstName("Jane")
                .lastName("Builder")
                .password("builderpass")
                .enabled(true)
                .role(testRole)
                .build();

        // Assert
        assertEquals(1L, builtUser.getId());
        assertEquals("builderuser", builtUser.getUsername());
        assertEquals("builder@test.com", builtUser.getEmail());
        assertEquals("Jane", builtUser.getFirstName());
        assertEquals("Builder", builtUser.getLastName());
        assertEquals("builderpass", builtUser.getPassword());
        assertTrue(builtUser.isEnabled());
        assertEquals(testRole, builtUser.getRole());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 100L;

        // Act
        user.setId(expectedId);

        // Assert
        assertEquals(expectedId, user.getId());
    }

    @Test
    void testSetAndGetUsername() {
        // Arrange
        String expectedUsername = "testuser";

        // Act
        user.setUsername(expectedUsername);

        // Assert
        assertEquals(expectedUsername, user.getUsername());
    }

    @Test
    void testSetAndGetEmail() {
        // Arrange
        String expectedEmail = "user@test.com";

        // Act
        user.setEmail(expectedEmail);

        // Assert
        assertEquals(expectedEmail, user.getEmail());
    }

    @Test
    void testSetAndGetFirstName() {
        // Arrange
        String expectedFirstName = "John";

        // Act
        user.setFirstName(expectedFirstName);

        // Assert
        assertEquals(expectedFirstName, user.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        // Arrange
        String expectedLastName = "Doe";

        // Act
        user.setLastName(expectedLastName);

        // Assert
        assertEquals(expectedLastName, user.getLastName());
    }

    @Test
    void testSetAndGetPassword() {
        // Arrange
        String expectedPassword = "password123";

        // Act
        user.setPassword(expectedPassword);

        // Assert
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    void testSetAndGetEnabled() {
        // Act
        user.setEnabled(true);

        // Assert
        assertTrue(user.isEnabled());

        // Act
        user.setEnabled(false);

        // Assert
        assertFalse(user.isEnabled());
    }

    @Test
    void testSetAndGetRole() {
        // Act
        user.setRole(testRole);

        // Assert
        assertEquals(testRole, user.getRole());
        assertEquals(1L, user.getRole().getId());
        assertEquals("USER", user.getRole().getName());
    }

    @Test
    void testGetColumnCount() {
        // Act
        int columnCount = user.getColumnCount();

        // Assert
        assertTrue(columnCount > 0);
        assertEquals(User.class.getDeclaredFields().length, columnCount);
    }

    @Test
    void testGetRole_id() {
        // Arrange
        user.setRole(testRole);

        // Act
        Long roleId = user.getRole_id();

        // Assert
        assertEquals(testRole.getId(), roleId);
        assertEquals(1L, roleId);
    }

    @Test
    void testGetRole_name() {
        // Arrange
        user.setRole(testRole);

        // Act
        String roleName = user.getRole_name();

        // Assert
        assertEquals(testRole.getName(), roleName);
        assertEquals("USER", roleName);
    }

    @Test
    void testGetName() {
        // Arrange
        user.setFirstName("John");
        user.setLastName("Doe");

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("John Doe", fullName);
    }

    @Test
    void testGetNameWithNullFirstName() {
        // Arrange
        user.setFirstName(null);
        user.setLastName("Doe");

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("null Doe", fullName);
    }

    @Test
    void testGetNameWithNullLastName() {
        // Arrange
        user.setFirstName("John");
        user.setLastName(null);

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("John null", fullName);
    }

    @Test
    void testGetNameWithBothNamesNull() {
        // Arrange
        user.setFirstName(null);
        user.setLastName(null);

        // Act
        String fullName = user.getName();

        // Assert
        assertEquals("null null", fullName);
    }

    @Test
    void testEmailValidation() {
        // Arrange
        user.setUsername("testuser");
        user.setEmail("invalid-email");

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("valid e-mail")));
    }

    @Test
    void testValidEmailValidation() {
        // Arrange
        user.setUsername("testuser");
        user.setEmail("valid@email.com");

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertTrue(violations.stream().noneMatch(v -> v.getMessage().contains("valid e-mail")));
    }

    @Test
    void testEmptyEmailValidation() {
        // Arrange
        user.setUsername("testuser");
        user.setEmail("");

        // Act
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("provide an e-mail")));
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        User user1 = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@test.com")
                .firstName("John")
                .lastName("Doe")
                .enabled(true)
                .build();

        User user2 = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@test.com")
                .firstName("John")
                .lastName("Doe")
                .enabled(true)
                .build();

        // Act & Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testToString() {
        // Arrange
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setFirstName("John");
        user.setLastName("Doe");

        // Act
        String toString = user.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id"));
        assertTrue(toString.contains("username"));
        assertTrue(toString.contains("email"));
    }

    @Test
    void testGetRole_idWithNullRole() {
        // Arrange
        user.setRole(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> user.getRole_id());
    }

    @Test
    void testGetRole_nameWithNullRole() {
        // Arrange
        user.setRole(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> user.getRole_name());
    }

    @Test
    void testAllFieldsWithNullValues() {
        // Act
        user.setId(null);
        user.setUsername(null);
        user.setEmail(null);
        user.setFirstName(null);
        user.setLastName(null);
        user.setPassword(null);
        user.setRole(null);

        // Assert
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getPassword());
        assertNull(user.getRole());
    }

    @Test
    void testPasswordHandling() {
        // Arrange
        String[] testPasswords = {"password", "123456", "complex!Password123", "", null};

        for (String password : testPasswords) {
            // Act
            user.setPassword(password);

            // Assert
            assertEquals(password, user.getPassword());
        }
    }

    @Test
    void testUsernameHandling() {
        // Arrange
        String[] testUsernames = {"user", "admin", "test_user", "user123", "user@domain"};

        for (String username : testUsernames) {
            // Act
            user.setUsername(username);

            // Assert
            assertEquals(username, user.getUsername());
        }
    }

    @Test
    void testRoleRelationship() {
        // Arrange
        Role adminRole = new Role();
        adminRole.setId(2L);
        adminRole.setName("ADMIN");

        // Act
        user.setRole(adminRole);

        // Assert
        assertEquals(adminRole, user.getRole());
        assertEquals(2L, user.getRole_id());
        assertEquals("ADMIN", user.getRole_name());
    }
}