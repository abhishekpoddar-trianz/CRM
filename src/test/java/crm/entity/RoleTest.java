package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ROLE_USER");
    }

    @Test
    public void testRoleConstructor() {
        Role newRole = new Role();
        assertNotNull(newRole);
        assertEquals(0, newRole.getId());
        assertNull(newRole.getName());
    }

    @Test
    public void testSettersAndGetters() {
        assertEquals(1, role.getId());
        assertEquals("ROLE_USER", role.getName());
    }

    @Test
    public void testSetId() {
        role.setId(2);
        assertEquals(2, role.getId());
    }

    @Test
    public void testSetName() {
        role.setName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    public void testRoleWithNullName() {
        role.setName(null);
        assertNull(role.getName());
    }

    @Test
    public void testMultipleRoles() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ROLE_ADMIN");

        assertNotEquals(role1.getId(), role2.getId());
        assertNotEquals(role1.getName(), role2.getName());
    }

    @Test
    public void testRoleNameUniqueness() {
        role.setName("UNIQUE_ROLE");
        assertEquals("UNIQUE_ROLE", role.getName());
    }
}
