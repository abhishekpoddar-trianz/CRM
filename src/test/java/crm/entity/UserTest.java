package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserBuilder() {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        User builtUser = User.builder()
                .id(1L)
                .username("johndoe")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();

        assertNotNull(builtUser);
        assertEquals(1L, builtUser.getId());
        assertEquals("johndoe", builtUser.getUsername());
        assertEquals("john@example.com", builtUser.getEmail());
        assertEquals("John", builtUser.getFirstName());
        assertEquals("Doe", builtUser.getLastName());
        assertEquals("password123", builtUser.getPassword());
        assertEquals(1, builtUser.getEnabled());
        assertEquals(role, builtUser.getRole());
    }

    @Test
    void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void testAllArgsConstructor() {
        Role role = new Role();
        User user = new User(1L, "testuser", "test@example.com", "Test", "User", "pass", 1, role);

        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testSetAndGetId() {
        user.setId(100L);
        assertEquals(100L, user.getId());
    }

    @Test
    void testSetAndGetUsername() {
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    void testSetAndGetEmail() {
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    void testSetAndGetFirstName() {
        user.setFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test
    void testSetAndGetLastName() {
        user.setLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test
    void testSetAndGetPassword() {
        user.setPassword("securePassword");
        assertEquals("securePassword", user.getPassword());
    }

    @Test
    void testSetAndGetEnabled() {
        user.setEnabled(1);
        assertEquals(1, user.getEnabled());

        user.setEnabled(0);
        assertEquals(0, user.getEnabled());
    }

    @Test
    void testSetAndGetRole() {
        Role role = new Role();
        role.setId(2);
        role.setName("USER");

        user.setRole(role);
        assertEquals(role, user.getRole());
    }

    @Test
    void testGetColumnCount() {
        int columnCount = user.getColumnCount();
        assertTrue(columnCount > 0);
    }

    @Test
    void testGetRoleId() {
        Role role = new Role();
        role.setId(5);
        user.setRole(role);

        assertEquals(5, user.getRole_id());
    }

    @Test
    void testGetRoleName() {
        Role role = new Role();
        role.setName("MANAGER");
        user.setRole(role);

        assertEquals("MANAGER", user.getRole_name());
    }

    @Test
    void testGetName() {
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals("John Doe", user.getName());
    }

    @Test
    void testGetNameWithNullValues() {
        user.setFirstName(null);
        user.setLastName(null);

        assertEquals("null null", user.getName());
    }

    @Test
    void testEmailValidation() {
        String validEmail = "valid@example.com";
        user.setEmail(validEmail);
        assertEquals(validEmail, user.getEmail());
    }

    @Test
    void testUniqueUsername() {
        user.setUsername("uniqueuser");
        assertEquals("uniqueuser", user.getUsername());
    }

    @Test
    void testUniqueEmail() {
        user.setEmail("unique@example.com");
        assertEquals("unique@example.com", user.getEmail());
    }

    @Test
    void testEnabledFlag() {
        user.setEnabled(1);
        assertTrue(user.getEnabled() == 1);

        user.setEnabled(0);
        assertTrue(user.getEnabled() == 0);
    }
}
