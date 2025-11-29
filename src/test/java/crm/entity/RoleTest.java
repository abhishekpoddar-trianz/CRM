package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
    }

    @Test
    void testConstructor() {
        Role newRole = new Role();
        assertNotNull(newRole);
    }

    @Test
    void testGettersAndSetters() {
        role.setId(1);
        role.setName("ROLE_USER");

        assertEquals(1, role.getId());
        assertEquals("ROLE_USER", role.getName());
    }

    @Test
    void testAllArgsConstructor() {
        Role newRole = new Role(1, "ROLE_ADMIN");
        assertEquals(1, newRole.getId());
        assertEquals("ROLE_ADMIN", newRole.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(1);
        role2.setName("ROLE_USER");

        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());
    }
}