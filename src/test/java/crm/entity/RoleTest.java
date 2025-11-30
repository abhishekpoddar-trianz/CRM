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
        Role role = new Role();
        assertNotNull(role);
    }

    @Test
    void testSetAndGetId() {
        role.setId(1);
        assertEquals(1, role.getId());
    }

    @Test
    void testSetAndGetName() {
        role.setName("ADMIN");
        assertEquals("ADMIN", role.getName());
    }

    @Test
    void testSetIdWithZero() {
        role.setId(0);
        assertEquals(0, role.getId());
    }

    @Test
    void testSetIdWithNegativeValue() {
        role.setId(-1);
        assertEquals(-1, role.getId());
    }

    @Test
    void testSetIdWithMaxValue() {
        int maxValue = Integer.MAX_VALUE;
        role.setId(maxValue);
        assertEquals(maxValue, role.getId());
    }

    @Test
    void testSetNameWithNull() {
        role.setName(null);
        assertNull(role.getName());
    }

    @Test
    void testSetNameWithEmptyString() {
        role.setName("");
        assertEquals("", role.getName());
    }

    @Test
    void testMultipleRolesWithDifferentNames() {
        Role role1 = new Role();
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setName("USER");

        assertNotEquals(role1.getName(), role2.getName());
    }

    @Test
    void testRoleNameWithSpecialCharacters() {
        String specialName = "SUPER_ADMIN";
        role.setName(specialName);
        assertEquals(specialName, role.getName());
    }

    @Test
    void testRoleNameUpperCase() {
        role.setName("MANAGER");
        assertEquals("MANAGER", role.getName());
    }

    @Test
    void testRoleNameLowerCase() {
        role.setName("manager");
        assertEquals("manager", role.getName());
    }

    @Test
    void testRoleNameMixedCase() {
        role.setName("Manager");
        assertEquals("Manager", role.getName());
    }

    @Test
    void testLongRoleName() {
        String longName = "A".repeat(100);
        role.setName(longName);
        assertEquals(longName, role.getName());
        assertEquals(100, role.getName().length());
    }

    @Test
    void testCommonRoleNames() {
        String[] commonRoles = {"ADMIN", "USER", "MANAGER", "GUEST", "MODERATOR"};

        for (String roleName : commonRoles) {
            Role testRole = new Role();
            testRole.setName(roleName);
            assertEquals(roleName, testRole.getName());
        }
    }

    @Test
    void testRoleWithNumericName() {
        role.setName("ROLE123");
        assertEquals("ROLE123", role.getName());
    }

    @Test
    void testRoleIdSequence() {
        role.setId(1);
        assertEquals(1, role.getId());

        role.setId(2);
        assertEquals(2, role.getId());

        role.setId(3);
        assertEquals(3, role.getId());
    }
}
