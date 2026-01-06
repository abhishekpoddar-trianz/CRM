package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");

        user = new User();
    }

    @Test
    void testUserBuilder() {
        User built = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();

        assertNotNull(built);
        assertEquals(1L, built.getId());
        assertEquals("testuser", built.getUsername());
        assertEquals("test@example.com", built.getEmail());
        assertEquals("John", built.getFirstName());
        assertEquals("Doe", built.getLastName());
        assertEquals("password123", built.getPassword());
        assertEquals(1, built.getEnabled());
        assertEquals(role, built.getRole());
    }

    @Test
    void testSettersAndGetters() {
        user.setId(2L);
        user.setUsername("user2");
        user.setEmail("user2@test.com");
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setPassword("pass456");
        user.setEnabled(1);
        user.setRole(role);

        assertEquals(2L, user.getId());
        assertEquals("user2", user.getUsername());
        assertEquals("user2@test.com", user.getEmail());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("pass456", user.getPassword());
        assertEquals(1, user.getEnabled());
        assertEquals(role, user.getRole());
    }

    @Test
    void testGetName() {
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals("John Doe", user.getName());
    }

    @Test
    void testGetRoleId() {
        user.setRole(role);
        assertEquals(1, user.getRole_id());
    }

    @Test
    void testGetRoleName() {
        user.setRole(role);
        assertEquals("ROLE_USER", user.getRole_name());
    }

    @Test
    void testGetColumnCount() {
        int columnCount = user.getColumnCount();
        assertTrue(columnCount > 0);
    }

    @Test
    void testNoArgsConstructor() {
        User newUser = new User();
        assertNotNull(newUser);
        assertNull(newUser.getId());
        assertNull(newUser.getUsername());
    }

    @Test
    void testAllArgsConstructor() {
        User fullUser = new User(
                3L,
                "fulluser",
                "full@test.com",
                "Full",
                "User",
                "fullpass",
                1,
                role
        );

        assertEquals(3L, fullUser.getId());
        assertEquals("fulluser", fullUser.getUsername());
        assertEquals("full@test.com", fullUser.getEmail());
        assertEquals("Full", fullUser.getFirstName());
        assertEquals("User", fullUser.getLastName());
        assertEquals("fullpass", fullUser.getPassword());
        assertEquals(1, fullUser.getEnabled());
        assertEquals(role, fullUser.getRole());
    }

    @Test
    void testEnabledField() {
        user.setEnabled(1);
        assertEquals(1, user.getEnabled());

        user.setEnabled(0);
        assertEquals(0, user.getEnabled());
    }
}
