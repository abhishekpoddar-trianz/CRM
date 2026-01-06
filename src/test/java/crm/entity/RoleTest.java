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
    void testSettersAndGetters() {
        role.setId(1);
        role.setName("ROLE_ADMIN");

        assertEquals(1, role.getId());
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    void testDefaultConstructor() {
        Role newRole = new Role();
        assertNotNull(newRole);
        assertEquals(0, newRole.getId());
        assertNull(newRole.getName());
    }

    @Test
    void testRoleNames() {
        role.setName("ROLE_USER");
        assertEquals("ROLE_USER", role.getName());

        role.setName("ROLE_MANAGER");
        assertEquals("ROLE_MANAGER", role.getName());

        role.setName("ROLE_OWNER");
        assertEquals("ROLE_OWNER", role.getName());
    }

    @Test
    void testIdField() {
        role.setId(5);
        assertEquals(5, role.getId());

        role.setId(10);
        assertEquals(10, role.getId());
    }

    @Test
    void testNullName() {
        role.setName(null);
        assertNull(role.getName());
    }
}
