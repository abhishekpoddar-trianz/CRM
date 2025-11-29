package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testNoArgsConstructor() {
        User newUser = new User();
        assertNotNull(newUser);
    }

    @Test
    public void testAllArgsConstructor() {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        User newUser = new User(1L, "username", "test@email.com", "John", "Doe", "password", 1, role);

        assertEquals(1L, newUser.getId());
        assertEquals("username", newUser.getUsername());
        assertEquals("test@email.com", newUser.getEmail());
        assertEquals("John", newUser.getFirstName());
        assertEquals("Doe", newUser.getLastName());
        assertEquals("password", newUser.getPassword());
        assertEquals(1, newUser.getEnabled());
        assertEquals(role, newUser.getRole());
    }

    @Test
    public void testBuilder() {
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User builtUser = User.builder()
                .id(2L)
                .username("testuser")
                .email("builder@test.com")
                .firstName("Jane")
                .lastName("Smith")
                .password("builderpass")
                .enabled(1)
                .role(role)
                .build();

        assertEquals(2L, builtUser.getId());
        assertEquals("testuser", builtUser.getUsername());
        assertEquals("builder@test.com", builtUser.getEmail());
        assertEquals("Jane", builtUser.getFirstName());
        assertEquals("Smith", builtUser.getLastName());
        assertEquals("builderpass", builtUser.getPassword());
        assertEquals(1, builtUser.getEnabled());
        assertEquals(role, builtUser.getRole());
    }

    @Test
    public void testSettersAndGetters() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password123");
        user.setEnabled(1);

        assertEquals(1L, user.getId());
        assertEquals("testuser", user.getUsername());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Test", user.getFirstName());
        assertEquals("User", user.getLastName());
        assertEquals("password123", user.getPassword());
        assertEquals(1, user.getEnabled());
    }

    @Test
    public void testRoleRelationship() {
        Role role = new Role();
        role.setId(1);
        role.setName("ADMIN");

        user.setRole(role);

        assertEquals(role, user.getRole());
        assertNotNull(user.getRole());
        assertEquals(1L, user.getRole().getId());
        assertEquals("ADMIN", user.getRole().getName());
    }

    @Test
    public void testGetColumnCount() {
        int columnCount = user.getColumnCount();
        assertTrue(columnCount > 0);
        assertEquals(8, columnCount); // id, username, email, firstName, lastName, password, enabled, role
    }

    @Test
    public void testGetRole_id() {
        Role role = new Role();
        role.setId(5);
        user.setRole(role);

        assertEquals(5, user.getRole_id());
    }

    @Test
    public void testGetRole_name() {
        Role role = new Role();
        role.setName("MANAGER");
        user.setRole(role);

        assertEquals("MANAGER", user.getRole_name());
    }

    @Test
    public void testGetName() {
        user.setFirstName("John");
        user.setLastName("Doe");

        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetNameWithNullValues() {
        user.setFirstName(null);
        user.setLastName(null);

        assertEquals("null null", user.getName());
    }

    @Test
    public void testGetNameWithPartialValues() {
        user.setFirstName("John");
        user.setLastName(null);

        assertEquals("John null", user.getName());

        user.setFirstName(null);
        user.setLastName("Doe");

        assertEquals("null Doe", user.getName());
    }

    @Test
    public void testGetRole_idWithNullRole() {
        user.setRole(null);

        assertThrows(NullPointerException.class, () -> {
            user.getRole_id();
        });
    }

    @Test
    public void testGetRole_nameWithNullRole() {
        user.setRole(null);

        assertThrows(NullPointerException.class, () -> {
            user.getRole_name();
        });
    }

    @Test
    public void testEntityAnnotation() {
        assertTrue(User.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        jakarta.persistence.Entity entityAnnotation = User.class.getAnnotation(jakarta.persistence.Entity.class);
        assertEquals("users", entityAnnotation.name());
    }

    @Test
    public void testLombokAnnotations() {
        assertTrue(User.class.isAnnotationPresent(lombok.Data.class));
        assertTrue(User.class.isAnnotationPresent(lombok.Builder.class));
        assertTrue(User.class.isAnnotationPresent(lombok.NoArgsConstructor.class));
        assertTrue(User.class.isAnnotationPresent(lombok.AllArgsConstructor.class));
    }

    @Test
    public void testEqualsAndHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("test");

        User user2 = new User();
        user2.setId(1L);
        user2.setUsername("test");

        assertEquals(user1, user2);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void testToString() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        String userString = user.toString();
        assertNotNull(userString);
        assertTrue(userString.contains("testuser"));
        assertTrue(userString.contains("test@example.com"));
    }

    @Test
    public void testEnabledField() {
        user.setEnabled(0);
        assertEquals(0, user.getEnabled());

        user.setEnabled(1);
        assertEquals(1, user.getEnabled());
    }

    @Test
    public void testValidationAnnotations() throws NoSuchFieldException {
        java.lang.reflect.Field emailField = User.class.getDeclaredField("email");
        assertTrue(emailField.isAnnotationPresent(jakarta.validation.constraints.Email.class));
        assertTrue(emailField.isAnnotationPresent(jakarta.validation.constraints.NotEmpty.class));
    }

    @Test
    public void testJpaAnnotations() throws NoSuchFieldException {
        java.lang.reflect.Field idField = User.class.getDeclaredField("id");
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.Id.class));
        assertTrue(idField.isAnnotationPresent(jakarta.persistence.GeneratedValue.class));

        java.lang.reflect.Field usernameField = User.class.getDeclaredField("username");
        assertTrue(usernameField.isAnnotationPresent(jakarta.persistence.Column.class));

        java.lang.reflect.Field roleField = User.class.getDeclaredField("role");
        assertTrue(roleField.isAnnotationPresent(jakarta.persistence.ManyToOne.class));
    }
}