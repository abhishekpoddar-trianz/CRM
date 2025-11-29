package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
        user.setRole(role);
    }

    @Test
    void testConstructor() {
        User newUser = new User();
        assertNotNull(newUser);
    }

    @Test
    void testBuilderConstructor() {
        User builtUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .enabled(1)
                .build();

        assertNotNull(builtUser);
        assertEquals(1L, builtUser.getId());
        assertEquals("testuser", builtUser.getUsername());
    }

    @Test
    void testAllArgsConstructor() {
        User newUser = new User(1L, "testuser", "test@test.com",
                               "Test", "User", "password", 1, role);
        assertNotNull(newUser);
        assertEquals(1L, newUser.getId());
        assertEquals("testuser", newUser.getUsername());
    }

    @Test
    void testGettersAndSetters() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password");
        user.setEnabled(1);

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@test.com", user.getEmail());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("password", user.getPassword());
        assertEquals(1, user.getEnabled());
    }

    @Test
    void testGetColumnCount() {
        int columnCount = user.getColumnCount();
        assertTrue(columnCount > 0);
    }

    @Test
    void testGetRoleId() {
        assertEquals(1, user.getRole_id());
    }

    @Test
    void testGetRoleName() {
        assertEquals("ROLE_USER", user.getRole_name());
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
    void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("test");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("test");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }
}