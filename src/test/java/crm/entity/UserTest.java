package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        user = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();
    }

    @Test
    public void testUserBuilder() {
        assertNotNull(user);
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
    public void testUserNoArgsConstructor() {
        User emptyUser = new User();
        assertNotNull(emptyUser);
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getUsername());
    }

    @Test
    public void testUserAllArgsConstructor() {
        User newUser = new User(2L, "newuser", "new@example.com", "Jane", "Smith", "pass456", 1, role);
        assertNotNull(newUser);
        assertEquals(2L, newUser.getId());
        assertEquals("newuser", newUser.getUsername());
        assertEquals("new@example.com", newUser.getEmail());
    }

    @Test
    public void testGetColumnCount() {
        int columnCount = user.getColumnCount();
        assertTrue(columnCount > 0);
    }

    @Test
    public void testGetRoleId() {
        int roleId = user.getRole_id();
        assertEquals(1, roleId);
    }

    @Test
    public void testGetRoleName() {
        String roleName = user.getRole_name();
        assertEquals("ROLE_USER", roleName);
    }

    @Test
    public void testGetName() {
        String fullName = user.getName();
        assertEquals("John Doe", fullName);
    }

    @Test
    public void testSettersAndGetters() {
        user.setUsername("updateduser");
        user.setEmail("updated@example.com");
        user.setFirstName("Updated");
        user.setLastName("User");
        user.setEnabled(0);

        assertEquals("updateduser", user.getUsername());
        assertEquals("updated@example.com", user.getEmail());
        assertEquals("Updated", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals(0, user.getEnabled());
    }

    @Test
    public void testUserWithNullRole() {
        user.setRole(null);
        assertNull(user.getRole());
    }

    @Test
    public void testEnabledValues() {
        user.setEnabled(1);
        assertEquals(1, user.getEnabled());

        user.setEnabled(0);
        assertEquals(0, user.getEnabled());
    }

    @Test
    public void testEmailValidation() {
        user.setEmail("valid@email.com");
        assertTrue(user.getEmail().contains("@"));
    }

    @Test
    public void testPasswordField() {
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());

        user.setPassword(null);
        assertNull(user.getPassword());
    }
}
