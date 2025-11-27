package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
    }

    @Test
    public void testRoleConstructor() {
        assertNotNull(role);
    }

    @Test
    public void testSetAndGetId() {
        role.setId(1);
        assertEquals(1, role.getId());
    }

    @Test
    public void testSetAndGetName() {
        role.setName("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", role.getName());
    }

    @Test
    public void testSetNameNull() {
        role.setName(null);
        assertNull(role.getName());
    }

    @Test
    public void testSetIdZero() {
        role.setId(0);
        assertEquals(0, role.getId());
    }

    @Test
    public void testSetIdNegative() {
        role.setId(-1);
        assertEquals(-1, role.getId());
    }

    @Test
    public void testRoleEquality() {
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("ROLE_USER");

        Role role2 = new Role();
        role2.setId(1);
        role2.setName("ROLE_USER");

        assertEquals(role1.getId(), role2.getId());
        assertEquals(role1.getName(), role2.getName());
    }

    @Test
    public void testToString() {
        role.setId(1);
        role.setName("ROLE_ADMIN");
        String result = role.toString();
        assertNotNull(result);
        assertTrue(result.contains("ROLE_ADMIN") || result.contains("1"));
    }
}
