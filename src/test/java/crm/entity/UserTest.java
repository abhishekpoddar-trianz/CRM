package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        user = new User();
        role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
    }

    @Test
    public void testUserConstructor() {
        assertNotNull(user);
    }

    @Test
    public void testUserBuilderConstructor() {
        User builtUser = User.builder()
                .id(1L)
                .username("john_doe")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();
        assertNotNull(builtUser);
        assertEquals(1L, builtUser.getId());
        assertEquals("john_doe", builtUser.getUsername());
    }

    @Test
    public void testAllArgsConstructor() {
        User userAll = new User(1L, "testuser", "test@test.com", "Test", "User", "pass", 1, role);
        assertNotNull(userAll);
        assertEquals(1L, userAll.getId());
        assertEquals("testuser", userAll.getUsername());
    }

    @Test
    public void testSetAndGetId() {
        user.setId(10L);
        assertEquals(10L, user.getId());
    }

    @Test
    public void testSetAndGetUsername() {
        user.setUsername("testuser");
        assertEquals("testuser", user.getUsername());
    }

    @Test
    public void testSetAndGetEmail() {
        user.setEmail("test@example.com");
        assertEquals("test@example.com", user.getEmail());
    }

    @Test
    public void testSetAndGetFirstName() {
        user.setFirstName("John");
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void testSetAndGetLastName() {
        user.setLastName("Doe");
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testSetAndGetPassword() {
        user.setPassword("securePass123");
        assertEquals("securePass123", user.getPassword());
    }

    @Test
    public void testSetAndGetEnabled() {
        user.setEnabled(1);
        assertEquals(1, user.getEnabled());
    }

    @Test
    public void testSetAndGetRole() {
        user.setRole(role);
        assertNotNull(user.getRole());
        assertEquals(1, user.getRole().getId());
    }

    @Test
    public void testGetColumnCount() {
        int count = user.getColumnCount();
        assertTrue(count > 0);
    }

    @Test
    public void testGetRoleId() {
        user.setRole(role);
        assertEquals(1, user.getRole_id());
    }

    @Test
    public void testGetRoleName() {
        user.setRole(role);
        assertEquals("ROLE_USER", user.getRole_name());
    }

    @Test
    public void testGetName() {
        user.setFirstName("John");
        user.setLastName("Doe");
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetNameWithNulls() {
        user.setFirstName(null);
        user.setLastName(null);
        assertEquals("null null", user.getName());
    }

    @Test
    public void testSetEnabledZero() {
        user.setEnabled(0);
        assertEquals(0, user.getEnabled());
    }

    @Test
    public void testSetRoleNull() {
        user.setRole(null);
        assertNull(user.getRole());
    }

    @Test
    public void testToString() {
        user.setUsername("testuser");
        String result = user.toString();
        assertNotNull(result);
    }
}
