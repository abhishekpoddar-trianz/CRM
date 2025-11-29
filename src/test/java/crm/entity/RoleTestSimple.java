package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class RoleTestSimple {

    private Role role;

    @BeforeEach
    public void setUp() {
        role = new Role();
    }

    @Test
    public void testDefaultConstructor() {
        Role newRole = new Role();
        assertNotNull(newRole);
    }

    @Test
    public void testSetAndGetId() {
        int expectedId = 1;
        role.setId(expectedId);
        assertEquals(expectedId, role.getId());
    }

    @Test
    public void testSetAndGetName() {
        String expectedName = "ADMIN";
        role.setName(expectedName);
        assertEquals(expectedName, role.getName());
    }

    @Test
    public void testSetAndGetNameWithNull() {
        role.setName(null);
        assertNull(role.getName());
    }

    @Test
    public void testToString() {
        role.setId(1);
        role.setName("ADMIN");
        String result = role.toString();
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("ADMIN"));
    }
}