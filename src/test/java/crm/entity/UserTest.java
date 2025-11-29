package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        role = new Role();
        role.setId(1);
        role.setName("ADMIN");
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
        assertEquals(0, newUser.getEnabled());
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
        int enabled = 1;

        // Act
        User user = new User(id, username, email, firstName, lastName, password, enabled, role);

        // Assert
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(password, user.getPassword());
        assertEquals(enabled, user.getEnabled());
        assertEquals(role, user.getRole());
    }

    @Test
    void testBuilder() {
        // Act
        User user = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password123", user.getPassword());
        assertEquals(1, user.getEnabled());
        assertEquals(role, user.getRole());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 123L;

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
        String expectedEmail = "test@example.com";

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
        // Arrange
        int expectedEnabled = 1;

        // Act
        user.setEnabled(expectedEnabled);

        // Assert
        assertEquals(expectedEnabled, user.getEnabled());
    }

    @Test
    void testSetAndGetRole() {
        // Act
        user.setRole(role);

        // Assert
        assertEquals(role, user.getRole());
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
        user.setRole(role);

        // Act
        int roleId = user.getRole_id();

        // Assert
        assertEquals(1, roleId);
    }

    @Test
    void testGetRole_idWithNullRole() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> user.getRole_id());
    }

    @Test
    void testGetRole_name() {
        // Arrange
        user.setRole(role);

        // Act
        String roleName = user.getRole_name();

        // Assert
        assertEquals("ADMIN", roleName);
    }

    @Test
    void testGetRole_nameWithNullRole() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> user.getRole_name());
    }

    @Test
    void testGetName() {
        // Arrange
        user.setFirstName("John");
        user.setLastName("Doe");

        // Act
        String name = user.getName();

        // Assert
        assertEquals("John Doe", name);
    }

    @Test
    void testGetNameWithNullFirstName() {
        // Arrange
        user.setFirstName(null);
        user.setLastName("Doe");

        // Act
        String name = user.getName();

        // Assert
        assertEquals("null Doe", name);
    }

    @Test
    void testGetNameWithNullLastName() {
        // Arrange
        user.setFirstName("John");
        user.setLastName(null);

        // Act
        String name = user.getName();

        // Assert
        assertEquals("John null", name);
    }

    @Test
    void testGetNameWithBothNamesNull() {
        // Arrange
        user.setFirstName(null);
        user.setLastName(null);

        // Act
        String name = user.getName();

        // Assert
        assertEquals("null null", name);
    }

    @Test
    void testGetNameWithEmptyNames() {
        // Arrange
        user.setFirstName("");
        user.setLastName("");

        // Act
        String name = user.getName();

        // Assert
        assertEquals(" ", name);
    }

    @Test
    void testSetNullValues() {
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
    void testSetEmptyStringValues() {
        // Act
        user.setUsername("");
        user.setEmail("");
        user.setFirstName("");
        user.setLastName("");
        user.setPassword("");

        // Assert
        assertEquals("", user.getUsername());
        assertEquals("", user.getEmail());
        assertEquals("", user.getFirstName());
        assertEquals("", user.getLastName());
        assertEquals("", user.getPassword());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        User user1 = User.builder()
                .id(1L)
                .username("test")
                .email("test@example.com")
                .build();

        User user2 = User.builder()
                .id(1L)
                .username("test")
                .email("test@example.com")
                .build();

        // Act & Assert
        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        User user1 = User.builder()
                .id(1L)
                .username("test1")
                .build();

        User user2 = User.builder()
                .id(2L)
                .username("test2")
                .build();

        // Act & Assert
        assertNotEquals(user1, user2);
    }

    @Test
    void testToString() {
        // Arrange
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        // Act
        String result = user.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("testuser"));
        assertTrue(result.contains("test@example.com"));
    }

    @Test
    void testAnnotationsPresent() {
        // Act & Assert
        assertTrue(User.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        assertTrue(User.class.isAnnotationPresent(lombok.Data.class));
        assertTrue(User.class.isAnnotationPresent(lombok.Builder.class));
        assertTrue(User.class.isAnnotationPresent(lombok.NoArgsConstructor.class));
        assertTrue(User.class.isAnnotationPresent(lombok.AllArgsConstructor.class));
    }
}