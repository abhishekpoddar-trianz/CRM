package crm.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

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
        assertEquals(0, newRole.getId());
        assertNull(newRole.getName());
    }

    @Test
    void testSetAndGetId() {
        // Arrange
        int expectedId = 123;

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
    void testSetIdWithZero() {
        // Act
        role.setId(0);

        // Assert
        assertEquals(0, role.getId());
    }

    @Test
    void testSetIdWithNegative() {
        // Act
        role.setId(-1);

        // Assert
        assertEquals(-1, role.getId());
    }

    @Test
    void testSetIdWithLargeValue() {
        // Arrange
        int largeId = Integer.MAX_VALUE;

        // Act
        role.setId(largeId);

        // Assert
        assertEquals(largeId, role.getId());
    }

    @Test
    void testSetNameWithNull() {
        // Act
        role.setName(null);

        // Assert
        assertNull(role.getName());
    }

    @Test
    void testSetNameWithEmpty() {
        // Act
        role.setName("");

        // Assert
        assertEquals("", role.getName());
    }

    @Test
    void testSetNameWithSpecialCharacters() {
        // Arrange
        String specialName = "ROLE_@#$%^&*()";

        // Act
        role.setName(specialName);

        // Assert
        assertEquals(specialName, role.getName());
    }

    @Test
    void testSetNameWithLongString() {
        // Arrange
        String longName = "A".repeat(1000);

        // Act
        role.setName(longName);

        // Assert
        assertEquals(longName, role.getName());
    }

    @Test
    void testEqualsAndHashCode() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("USER");

        Role role2 = new Role();
        role2.setId(1);
        role2.setName("USER");

        // Act & Assert
        assertEquals(role1, role2);
        assertEquals(role1.hashCode(), role2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Arrange
        Role role1 = new Role();
        role1.setId(1);
        role1.setName("USER");

        Role role2 = new Role();
        role2.setId(2);
        role2.setName("ADMIN");

        // Act & Assert
        assertNotEquals(role1, role2);
    }

    @Test
    void testToString() {
        // Arrange
        role.setId(1);
        role.setName("ADMIN");

        // Act
        String result = role.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
        assertTrue(result.contains("ADMIN"));
    }

    @Test
    void testToStringWithNullName() {
        // Arrange
        role.setId(1);
        role.setName(null);

        // Act
        String result = role.toString();

        // Assert
        assertNotNull(result);
        assertTrue(result.contains("1"));
    }

    @Test
    void testEntityAnnotationPresent() {
        // Act & Assert
        assertTrue(Role.class.isAnnotationPresent(jakarta.persistence.Entity.class));
        assertTrue(Role.class.isAnnotationPresent(jakarta.persistence.Table.class));
    }

    @Test
    void testLombokDataAnnotation() {
        // Act & Assert
        assertTrue(Role.class.isAnnotationPresent(lombok.Data.class));
    }
}