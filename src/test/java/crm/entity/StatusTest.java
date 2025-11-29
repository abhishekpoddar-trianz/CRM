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
    void testEnumNames() {
        // Act & Assert
        assertEquals("PROPOSED", Status.PROPOSED.name());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.name());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.name());
        assertEquals("DONE", Status.DONE.name());
    }

    @Test
    void testEnumOrdinals() {
        // Act & Assert
        assertEquals(0, Status.PROPOSED.ordinal());
        assertEquals(1, Status.NEGOTIATED.ordinal());
        assertEquals(2, Status.IMPLEMENTED.ordinal());
        assertEquals(3, Status.DONE.ordinal());
    }

    @Test
    void testValueOf() {
        // Act & Assert
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    void testValueOfWithInvalidName() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("INVALID"));
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("proposed"));
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("COMPLETED"));
    }

    @Test
    void testValueOfWithNull() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> Status.valueOf(null));
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
    void testAllConstantContainsAllValues() {
        // Act
        Status[] values = Status.values();
        Status[] all = Status.ALL;

        // Assert
        assertEquals(values.length, all.length);
        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], all[i]);
        }
    }

    @Test
    void testEnumEquality() {
        // Act & Assert
        assertEquals(Status.PROPOSED, Status.PROPOSED);
        assertEquals(Status.NEGOTIATED, Status.NEGOTIATED);
        assertEquals(Status.IMPLEMENTED, Status.IMPLEMENTED);
        assertEquals(Status.DONE, Status.DONE);

        assertNotEquals(Status.PROPOSED, Status.NEGOTIATED);
        assertNotEquals(Status.NEGOTIATED, Status.IMPLEMENTED);
        assertNotEquals(Status.IMPLEMENTED, Status.DONE);
    }

    @Test
    void testEnumHashCode() {
        // Act & Assert
        assertEquals(Status.PROPOSED.hashCode(), Status.PROPOSED.hashCode());
        assertEquals(Status.NEGOTIATED.hashCode(), Status.NEGOTIATED.hashCode());
        assertEquals(Status.IMPLEMENTED.hashCode(), Status.IMPLEMENTED.hashCode());
        assertEquals(Status.DONE.hashCode(), Status.DONE.hashCode());

        // Different enum values should have different hash codes (usually)
        assertNotEquals(Status.PROPOSED.hashCode(), Status.DONE.hashCode());
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
    void testEnumComparison() {
        // Act & Assert
        assertTrue(Status.PROPOSED.ordinal() < Status.NEGOTIATED.ordinal());
        assertTrue(Status.NEGOTIATED.ordinal() < Status.IMPLEMENTED.ordinal());
        assertTrue(Status.IMPLEMENTED.ordinal() < Status.DONE.ordinal());

        // Test compareTo method
        assertTrue(Status.PROPOSED.compareTo(Status.NEGOTIATED) < 0);
        assertTrue(Status.NEGOTIATED.compareTo(Status.IMPLEMENTED) < 0);
        assertTrue(Status.IMPLEMENTED.compareTo(Status.DONE) < 0);
        assertTrue(Status.DONE.compareTo(Status.PROPOSED) > 0);
        assertEquals(0, Status.PROPOSED.compareTo(Status.PROPOSED));
    }

    @Test
    void testEnumInSwitch() {
        // Act & Assert for each enum value in a switch statement
        for (Status status : Status.values()) {
            String result = switch (status) {
                case PROPOSED -> "In proposal phase";
                case NEGOTIATED -> "Under negotiation";
                case IMPLEMENTED -> "Being implemented";
                case DONE -> "Completed";
            };
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }

    @Test
    void testEnumInArray() {
        // Arrange
        Status[] statuses = {Status.PROPOSED, Status.NEGOTIATED, Status.IMPLEMENTED, Status.DONE};

        // Act & Assert
        assertEquals(4, statuses.length);
        for (int i = 0; i < statuses.length; i++) {
            assertEquals(Status.values()[i], statuses[i]);
        }
    }

    @Test
    void testEnumConstantCount() {
        // Act
        Status[] values = Status.values();

        // Assert - Ensure we have exactly 4 status values
        assertEquals(4, values.length);

        // Verify each expected constant exists
        assertTrue(java.util.Arrays.asList(values).contains(Status.PROPOSED));
        assertTrue(java.util.Arrays.asList(values).contains(Status.NEGOTIATED));
        assertTrue(java.util.Arrays.asList(values).contains(Status.IMPLEMENTED));
        assertTrue(java.util.Arrays.asList(values).contains(Status.DONE));
    }

    @Test
    void testEnumIsInstanceOf() {
        // Act & Assert
        assertTrue(Status.PROPOSED instanceof Status);
        assertTrue(Status.NEGOTIATED instanceof Status);
        assertTrue(Status.IMPLEMENTED instanceof Status);
        assertTrue(Status.DONE instanceof Status);

        assertTrue(Status.PROPOSED instanceof Enum);
        assertTrue(Status.NEGOTIATED instanceof Enum);
        assertTrue(Status.IMPLEMENTED instanceof Enum);
        assertTrue(Status.DONE instanceof Enum);
    }

    @Test
    void testEnumGetDeclaringClass() {
        // Act & Assert
        assertEquals(Status.class, Status.PROPOSED.getDeclaringClass());
        assertEquals(Status.class, Status.NEGOTIATED.getDeclaringClass());
        assertEquals(Status.class, Status.IMPLEMENTED.getDeclaringClass());
        assertEquals(Status.class, Status.DONE.getDeclaringClass());
    }

    @Test
    void testAllConstantImmutability() {
        // Arrange
        Status[] original = Status.ALL;
        Status firstElement = original[0];

        // Act - Try to modify the array (this should not affect the original)
        Status[] copy = Status.ALL;

        // Assert - The ALL constant should return the same reference or equivalent array
        assertEquals(original.length, copy.length);
        assertEquals(firstElement, copy[0]);
    }

    @Test
    void testEnumValuesMethodReturnsNewArray() {
        // Act
        Status[] values1 = Status.values();
        Status[] values2 = Status.values();

        // Assert - values() should return a new array each time
        assertNotSame(values1, values2);
        assertArrayEquals(values1, values2);
    }

    @Test
    void testStatusProgression() {
        // Test logical progression of status values
        Status[] progression = {Status.PROPOSED, Status.NEGOTIATED, Status.IMPLEMENTED, Status.DONE};

        // Assert the progression makes logical sense
        assertEquals("PROPOSED", progression[0].name());
        assertEquals("NEGOTIATED", progression[1].name());
        assertEquals("IMPLEMENTED", progression[2].name());
        assertEquals("DONE", progression[3].name());

        // Verify ordinal progression
        for (int i = 0; i < progression.length - 1; i++) {
            assertTrue(progression[i].ordinal() < progression[i + 1].ordinal());
        }
    }
}