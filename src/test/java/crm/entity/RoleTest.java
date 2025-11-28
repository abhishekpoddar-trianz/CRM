package crm.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setName("ADMIN");
    }

    @Test
    void testConstructor() {
        Role newRole = new Role();
        assertNotNull(newRole);
    }

    @Test
    void testSettersAndGetters() {
        assertEquals(1, role.getId());
        assertEquals("ADMIN", role.getName());
    }

    @Test
    void testSetId() {
        role.setId(10);
        assertEquals(10, role.getId());
    }

    @Test
    void testSetName() {
        role.setName("USER");
        assertEquals("USER", role.getName());
    }

    @Test
    void testNullName() {
        role.setName(null);
        assertNull(role.getName());
    }

    @Test
    void testEmptyName() {
        role.setName("");
        assertEquals("", role.getName());
    }

    @Test
    void testDifferentRoleNames() {
        role.setName("ADMIN");
        assertEquals("ADMIN", role.getName());

        role.setName("USER");
        assertEquals("USER", role.getName());

        role.setName("MODERATOR");
        assertEquals("MODERATOR", role.getName());
    }

    @Test
    void testIdAutoGeneration() {
        Role newRole = new Role();
        assertEquals(0, newRole.getId());
    }

    @Test
    void testRoleUniqueness() {
        Role role1 = new Role();
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setName("ADMIN");

        assertEquals(role1.getName(), role2.getName());
    }

    @Test
    void testLongRoleName() {
        String longName = "A".repeat(100);
        role.setName(longName);
        assertEquals(longName, role.getName());
    }
}
