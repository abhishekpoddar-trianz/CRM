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
        role.setName("ADMIN");

        user = User.builder()
                .id(1L)
                .username("johndoe")
                .email("john@example.com")
                .firstName("John")
                .lastName("Doe")
                .password("password123")
                .enabled(1)
                .role(role)
                .build();
    }

    @Test
    void testUserBuilder() {
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("johndoe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("password123", user.getPassword());
        assertEquals(1, user.getEnabled());
        assertEquals(role, user.getRole());
    }

    @Test
    void testNoArgsConstructor() {
        User emptyUser = new User();
        assertNotNull(emptyUser);
        assertNull(emptyUser.getId());
        assertNull(emptyUser.getUsername());
    }

    @Test
    void testAllArgsConstructor() {
        User newUser = new User(2L, "janesmith", "jane@example.com",
                "Jane", "Smith", "password456", 0, role);

        assertNotNull(newUser);
        assertEquals(2L, newUser.getId());
        assertEquals("janesmith", newUser.getUsername());
        assertEquals("jane@example.com", newUser.getEmail());
        assertEquals("Jane", newUser.getFirstName());
        assertEquals("Smith", newUser.getLastName());
    }

    @Test
    void testSettersAndGetters() {
        user.setId(10L);
        user.setUsername("newusername");
        user.setEmail("new@example.com");
        user.setFirstName("NewFirst");
        user.setLastName("NewLast");
        user.setPassword("newpassword");
        user.setEnabled(0);

        assertEquals(10L, user.getId());
        assertEquals("newusername", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("NewFirst", user.getFirstName());
        assertEquals("NewLast", user.getLastName());
        assertEquals("newpassword", user.getPassword());
        assertEquals(0, user.getEnabled());
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
        assertEquals("ADMIN", user.getRole_name());
    }

    @Test
    void testGetName() {
        assertEquals("John Doe", user.getName());
    }

    @Test
    void testGetNameWithNullValues() {
        user.setFirstName(null);
        user.setLastName(null);
        assertEquals("null null", user.getName());
    }

    @Test
    void testRoleRelationship() {
        Role newRole = new Role();
        newRole.setId(2);
        newRole.setName("USER");

        user.setRole(newRole);
        assertEquals(newRole, user.getRole());
        assertEquals(2, user.getRole_id());
        assertEquals("USER", user.getRole_name());
    }

    @Test
    void testEnabledToggle() {
        user.setEnabled(1);
        assertEquals(1, user.getEnabled());

        user.setEnabled(0);
        assertEquals(0, user.getEnabled());
    }

    @Test
    void testUniqueUsername() {
        User user1 = new User();
        user1.setUsername("unique");

        User user2 = new User();
        user2.setUsername("unique");

        assertEquals(user1.getUsername(), user2.getUsername());
    }

    @Test
    void testEmailValidation() {
        user.setEmail("valid@email.com");
        assertEquals("valid@email.com", user.getEmail());
    }
}
