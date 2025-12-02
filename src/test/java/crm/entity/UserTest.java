package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
    public void testSetAndGetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
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
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetAndGetEnabled() {
        user.setEnabled(1);
        assertEquals(1, user.getEnabled());
    }

    @Test
    public void testSetAndGetRole() {
        user.setRole(role);
        assertEquals(role, user.getRole());
    }

    @Test
    public void testGetColumnCount() {
        int columnCount = user.getColumnCount();
        assertTrue(columnCount > 0);
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
    public void testBuilderPattern() {
        User userBuilt = User.builder()
                .id(1L)
                .username("john")
                .email("john@test.com")
                .firstName("John")
                .lastName("Doe")
                .password("pass")
                .enabled(1)
                .role(role)
                .build();

        assertEquals(1L, userBuilt.getId());
        assertEquals("john", userBuilt.getUsername());
        assertEquals("john@test.com", userBuilt.getEmail());
        assertEquals("John", userBuilt.getFirstName());
        assertEquals("Doe", userBuilt.getLastName());
        assertEquals("pass", userBuilt.getPassword());
        assertEquals(1, userBuilt.getEnabled());
        assertEquals(role, userBuilt.getRole());
    }

    @Test
    public void testNoArgsConstructor() {
        User emptyUser = new User();
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getUsername());
        assertNull(emptyUser.getEmail());
    }

    @Test
    public void testEnabledZero() {
        user.setEnabled(0);
        assertEquals(0, user.getEnabled());
    }
}
