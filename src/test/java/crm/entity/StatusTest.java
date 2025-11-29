package crm.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void status_shouldHaveAllExpectedValues() {
        // Assert
        assertEquals(4, Status.values().length, "Status should have 4 values");

        Status[] expectedValues = {Status.PROPOSED, Status.NEGOTIATED, Status.IMPLEMENTED, Status.DONE};
        assertArrayEquals(expectedValues, Status.values(), "Status values should match expected order");
    }

    @Test
    void status_proposedValue_shouldExist() {
        // Act & Assert
        assertNotNull(Status.PROPOSED, "PROPOSED status should exist");
        assertEquals("PROPOSED", Status.PROPOSED.toString(), "PROPOSED should have correct string representation");
    }

    @Test
    void status_negotiatedValue_shouldExist() {
        // Act & Assert
        assertNotNull(Status.NEGOTIATED, "NEGOTIATED status should exist");
        assertEquals("NEGOTIATED", Status.NEGOTIATED.toString(), "NEGOTIATED should have correct string representation");
    }

    @Test
    void status_implementedValue_shouldExist() {
        // Act & Assert
        assertNotNull(Status.IMPLEMENTED, "IMPLEMENTED status should exist");
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.toString(), "IMPLEMENTED should have correct string representation");
    }

    @Test
    void status_doneValue_shouldExist() {
        // Act & Assert
        assertNotNull(Status.DONE, "DONE status should exist");
        assertEquals("DONE", Status.DONE.toString(), "DONE should have correct string representation");
    }

    @Test
    void status_allArray_shouldContainAllValues() {
        // Assert
        assertNotNull(Status.ALL, "ALL array should not be null");
        assertEquals(4, Status.ALL.length, "ALL array should contain 4 elements");

        assertEquals(Status.PROPOSED, Status.ALL[0], "First element should be PROPOSED");
        assertEquals(Status.NEGOTIATED, Status.ALL[1], "Second element should be NEGOTIATED");
        assertEquals(Status.IMPLEMENTED, Status.ALL[2], "Third element should be IMPLEMENTED");
        assertEquals(Status.DONE, Status.ALL[3], "Fourth element should be DONE");
    }

    @Test
    void status_allArray_shouldMatchValuesArray() {
        // Act
        Status[] values = Status.values();
        Status[] all = Status.ALL;

        // Assert
        assertEquals(values.length, all.length, "ALL array should have same length as values()");

        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], all[i], "Element at index " + i + " should match between values() and ALL");
        }
    }

    @Test
    void status_valueOf_shouldReturnCorrectValues() {
        // Act & Assert
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"), "valueOf should return PROPOSED");
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"), "valueOf should return NEGOTIATED");
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"), "valueOf should return IMPLEMENTED");
        assertEquals(Status.DONE, Status.valueOf("DONE"), "valueOf should return DONE");
    }

    @Test
    void status_valueOf_withInvalidValue_shouldThrowException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("INVALID"),
                "valueOf should throw IllegalArgumentException for invalid value");
    }

    @Test
    void status_valueOf_withNullValue_shouldThrowException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> Status.valueOf(null),
                "valueOf should throw NullPointerException for null value");
    }

    @Test
    void status_ordinal_shouldReturnCorrectValues() {
        // Act & Assert
        assertEquals(0, Status.PROPOSED.ordinal(), "PROPOSED ordinal should be 0");
        assertEquals(1, Status.NEGOTIATED.ordinal(), "NEGOTIATED ordinal should be 1");
        assertEquals(2, Status.IMPLEMENTED.ordinal(), "IMPLEMENTED ordinal should be 2");
        assertEquals(3, Status.DONE.ordinal(), "DONE ordinal should be 3");
    }

    @Test
    void status_name_shouldReturnCorrectNames() {
        // Act & Assert
        assertEquals("PROPOSED", Status.PROPOSED.name(), "PROPOSED name should be 'PROPOSED'");
        assertEquals("NEGOTIATED", Status.NEGOTIATED.name(), "NEGOTIATED name should be 'NEGOTIATED'");
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.name(), "IMPLEMENTED name should be 'IMPLEMENTED'");
        assertEquals("DONE", Status.DONE.name(), "DONE name should be 'DONE'");
    }

    @Test
    void status_compareTo_shouldWorkCorrectly() {
        // Act & Assert
        assertTrue(Status.PROPOSED.compareTo(Status.NEGOTIATED) < 0, "PROPOSED should be less than NEGOTIATED");
        assertTrue(Status.NEGOTIATED.compareTo(Status.IMPLEMENTED) < 0, "NEGOTIATED should be less than IMPLEMENTED");
        assertTrue(Status.IMPLEMENTED.compareTo(Status.DONE) < 0, "IMPLEMENTED should be less than DONE");

        assertEquals(0, Status.PROPOSED.compareTo(Status.PROPOSED), "Status should be equal to itself");

        assertTrue(Status.DONE.compareTo(Status.PROPOSED) > 0, "DONE should be greater than PROPOSED");
    }

    @Test
    void status_equals_shouldWorkCorrectly() {
        // Act & Assert
        assertEquals(Status.PROPOSED, Status.PROPOSED, "Status should equal itself");
        assertNotEquals(Status.PROPOSED, Status.NEGOTIATED, "Different statuses should not be equal");
        assertNotEquals(Status.PROPOSED, null, "Status should not equal null");
        assertNotEquals(Status.PROPOSED, "PROPOSED", "Status should not equal string");
    }

    @Test
    void status_hashCode_shouldBeConsistent() {
        // Act & Assert
        assertEquals(Status.PROPOSED.hashCode(), Status.PROPOSED.hashCode(), "Hash code should be consistent");
        assertNotEquals(Status.PROPOSED.hashCode(), Status.NEGOTIATED.hashCode(), "Different statuses should have different hash codes");
    }

    @Test
    void status_allArrayConstant_shouldBeFinal() throws NoSuchFieldException {
        // Arrange
        var allField = Status.class.getDeclaredField("ALL");

        // Assert
        assertTrue(java.lang.reflect.Modifier.isStatic(allField.getModifiers()), "ALL field should be static");
        assertTrue(java.lang.reflect.Modifier.isFinal(allField.getModifiers()), "ALL field should be final");
        assertTrue(java.lang.reflect.Modifier.isPublic(allField.getModifiers()), "ALL field should be public");
    }

    @Test
    void status_isEnum_shouldReturnTrue() {
        // Assert
        assertTrue(Status.class.isEnum(), "Status should be an enum");
        assertEquals(Enum.class, Status.class.getSuperclass(), "Status should extend Enum");
    }
}