package crm.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testEnumValues() {
        // Act
        Status[] values = Status.values();

        // Assert
        assertEquals(4, values.length);
        assertEquals(Status.PROPOSED, values[0]);
        assertEquals(Status.NEGOTIATED, values[1]);
        assertEquals(Status.IMPLEMENTED, values[2]);
        assertEquals(Status.DONE, values[3]);
    }

    @Test
    void testEnumValueOf() {
        // Act & Assert
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    void testEnumValueOfInvalidValue() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("INVALID"));
    }

    @Test
    void testEnumValueOfNullValue() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> Status.valueOf(null));
    }

    @Test
    void testEnumValueOfCaseSensitive() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("proposed"));
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("Proposed"));
    }

    @Test
    void testAllConstant() {
        // Act
        Status[] all = Status.ALL;

        // Assert
        assertNotNull(all);
        assertEquals(4, all.length);
        assertEquals(Status.PROPOSED, all[0]);
        assertEquals(Status.NEGOTIATED, all[1]);
        assertEquals(Status.IMPLEMENTED, all[2]);
        assertEquals(Status.DONE, all[3]);
    }

    @Test
    void testAllConstantImmutability() {
        // Arrange
        Status[] originalAll = Status.ALL;
        Status[] copyOfAll = Status.ALL.clone();

        // Act - Attempt to modify the array reference (this won't affect the original)
        originalAll = new Status[]{Status.DONE};

        // Assert - The actual Status.ALL should remain unchanged
        assertArrayEquals(copyOfAll, Status.ALL);
        assertEquals(4, Status.ALL.length);
    }

    @Test
    void testEnumName() {
        // Act & Assert
        assertEquals("PROPOSED", Status.PROPOSED.name());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.name());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.name());
        assertEquals("DONE", Status.DONE.name());
    }

    @Test
    void testEnumOrdinal() {
        // Act & Assert
        assertEquals(0, Status.PROPOSED.ordinal());
        assertEquals(1, Status.NEGOTIATED.ordinal());
        assertEquals(2, Status.IMPLEMENTED.ordinal());
        assertEquals(3, Status.DONE.ordinal());
    }

    @Test
    void testEnumToString() {
        // Act & Assert
        assertEquals("PROPOSED", Status.PROPOSED.toString());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.toString());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.toString());
        assertEquals("DONE", Status.DONE.toString());
    }

    @Test
    void testEnumEquality() {
        // Act & Assert
        assertEquals(Status.PROPOSED, Status.PROPOSED);
        assertNotEquals(Status.PROPOSED, Status.NEGOTIATED);
        assertNotEquals(Status.NEGOTIATED, Status.IMPLEMENTED);
        assertNotEquals(Status.IMPLEMENTED, Status.DONE);
    }

    @Test
    void testEnumHashCode() {
        // Act & Assert
        assertEquals(Status.PROPOSED.hashCode(), Status.PROPOSED.hashCode());
        assertNotEquals(Status.PROPOSED.hashCode(), Status.NEGOTIATED.hashCode());
    }

    @Test
    void testEnumComparison() {
        // Act & Assert
        assertTrue(Status.PROPOSED.compareTo(Status.NEGOTIATED) < 0);
        assertTrue(Status.NEGOTIATED.compareTo(Status.IMPLEMENTED) < 0);
        assertTrue(Status.IMPLEMENTED.compareTo(Status.DONE) < 0);
        assertTrue(Status.DONE.compareTo(Status.PROPOSED) > 0);
        assertEquals(0, Status.PROPOSED.compareTo(Status.PROPOSED));
    }

    @Test
    void testEnumInSwitch() {
        // Arrange & Act & Assert
        for (Status status : Status.values()) {
            String result = switch (status) {
                case PROPOSED -> "Contract proposed";
                case NEGOTIATED -> "Contract negotiated";
                case IMPLEMENTED -> "Contract implemented";
                case DONE -> "Contract done";
            };
            assertNotNull(result);
            assertTrue(result.contains("Contract"));
        }
    }

    @Test
    void testAllConstantContainsAllEnumValues() {
        // Arrange
        Status[] enumValues = Status.values();
        Status[] allConstant = Status.ALL;

        // Act & Assert
        assertEquals(enumValues.length, allConstant.length);
        for (int i = 0; i < enumValues.length; i++) {
            assertEquals(enumValues[i], allConstant[i]);
        }
    }

    @Test
    void testEnumIsInstance() {
        // Act & Assert
        assertTrue(Status.PROPOSED instanceof Status);
        assertTrue(Status.NEGOTIATED instanceof Status);
        assertTrue(Status.IMPLEMENTED instanceof Status);
        assertTrue(Status.DONE instanceof Status);
    }

    @Test
    void testEnumClass() {
        // Act & Assert
        assertEquals(Status.class, Status.PROPOSED.getClass());
        assertEquals(Status.class, Status.NEGOTIATED.getClass());
        assertEquals(Status.class, Status.IMPLEMENTED.getClass());
        assertEquals(Status.class, Status.DONE.getClass());
    }

    @Test
    void testEnumDeclaringClass() {
        // Act & Assert
        assertEquals(Status.class, Status.PROPOSED.getDeclaringClass());
        assertEquals(Status.class, Status.NEGOTIATED.getDeclaringClass());
        assertEquals(Status.class, Status.IMPLEMENTED.getDeclaringClass());
        assertEquals(Status.class, Status.DONE.getDeclaringClass());
    }
}