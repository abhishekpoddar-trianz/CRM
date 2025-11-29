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
    void testDefaultConstructor() {
        // Act
        Role newRole = new Role();

        // Assert
        assertNotNull(newRole);
        assertNull(newRole.getId());
        assertNull(newRole.getName());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        Long expectedId = 1L;

        // Act
        role.setId(expectedId);

        // Assert
        assertEquals(expectedId, role.getId());
    }

    @Test
    void testSetAndGetName() {
        // Arrange
        String expectedName = "ADMIN";

        // Act
        role.setName(expectedName);

        // Assert
        assertEquals(expectedName, role.getName());
    }

    @Test
    void testSetIdWithNull() {
        // Act
        role.setId(null);

        // Assert
        assertNull(role.getId());
    }

    @Test
    void testSetNameWithNull() {
        // Act
        role.setName(null);

        // Assert
        assertNull(role.getName());
    }

    @Test
    void testSetIdWithDifferentValues() {
        // Test with various ID values
        Long[] testIds = {0L, 1L, 100L, Long.MAX_VALUE};

        for (Long testId : testIds) {
            // Act
            role.setId(testId);

            // Assert
            assertEquals(testId, role.getId());
        }
    }

    @Test
    void testSetNameWithDifferentValues() {
        // Arrange
        String[] testNames = {"USER", "ADMIN", "MANAGER", "GUEST", ""};

        for (String testName : testNames) {
            // Act
            role.setName(testName);

            // Assert
            assertEquals(testName, role.getName());
        }
    }

    @Test
    void testSetNameWithEmptyString() {
        // Arrange
        String emptyName = "";

        // Act
        role.setName(emptyName);

        // Assert
        assertEquals(emptyName, role.getName());
        assertTrue(role.getName().isEmpty());
    }

    @Test
    void testSetNameWithWhitespace() {
        // Arrange
        String whitespaceName = "   ";

        // Act
        role.setName(whitespaceName);

        // Assert
        assertEquals(whitespaceName, role.getName());
    }

    @Test
    void testRoleEntityProperties() {
        // Arrange
        Long id = 5L;
        String name = "MODERATOR";

        // Act
        role.setId(id);
        role.setName(name);

        // Assert
        assertEquals(id, role.getId());
        assertEquals(name, role.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Role role1 = new Role();
        Role role2 = new Role();
        role1.setId(1L);
        role1.setName("ADMIN");
        role2.setId(1L);
        role2.setName("ADMIN");

        // Act & Assert
        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        Role role1 = new Role();
        Role role2 = new Role();
        role1.setId(1L);
        role1.setName("ADMIN");
        role2.setId(2L);
        role2.setName("USER");

        // Act & Assert
        assertNotEquals(role1, role2);
    }

    @Test
    void testToString() {
        // Arrange
        role.setId(1L);
        role.setName("ADMIN");

        // Act
        String toString = role.toString();

        // Assert
        assertNotNull(toString);
        assertTrue(toString.contains("id"));
        assertTrue(toString.contains("name"));
    }

    @Test
    void testLombokGeneratedMethods() {
        // Test that Lombok @Data generates equals, hashCode, and toString
        Role role1 = new Role();
        Role role2 = new Role();

        role1.setId(10L);
        role1.setName("TEST_ROLE");

        role2.setId(10L);
        role2.setName("TEST_ROLE");

        // Assert equals and hashCode work
        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());

        // Assert toString works
        String str = role1.toString();
        assertNotNull(str);
        assertTrue(str.length() > 0);
    }

    @Test
    void testRoleNamesWithSpecialCharacters() {
        // Arrange
        String[] specialNames = {"ROLE_USER", "role-admin", "user@domain", "user.name"};

        for (String specialName : specialNames) {
            // Act
            role.setName(specialName);

            // Assert
            assertEquals(specialName, role.getName());
        }
    }

    @Test
    void testIdBoundaryValues() {
        // Test with boundary values
        Long[] boundaryIds = {Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE};

        for (Long boundaryId : boundaryIds) {
            // Act
            role.setId(boundaryId);

            // Assert
            assertEquals(boundaryId, role.getId());
        }
    }
}