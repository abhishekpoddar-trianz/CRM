package crm.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    @Test
    public void testStatusEnumValues() {
        assertEquals(4, Status.values().length);
        assertNotNull(Status.PROPOSED);
        assertNotNull(Status.NEGOTIATED);
        assertNotNull(Status.IMPLEMENTED);
        assertNotNull(Status.DONE);
    }

    @Test
    public void testStatusValueOf() {
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    public void testStatusToString() {
        assertEquals("PROPOSED", Status.PROPOSED.toString());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.toString());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.toString());
        assertEquals("DONE", Status.DONE.toString());
    }

    @Test
    public void testStatusOrdinal() {
        assertEquals(0, Status.PROPOSED.ordinal());
        assertEquals(1, Status.NEGOTIATED.ordinal());
        assertEquals(2, Status.IMPLEMENTED.ordinal());
        assertEquals(3, Status.DONE.ordinal());
    }

    @Test
    public void testStatusALLConstant() {
        Status[] allStatuses = Status.ALL;
        assertNotNull(allStatuses);
        assertEquals(4, allStatuses.length);
        assertEquals(Status.PROPOSED, allStatuses[0]);
        assertEquals(Status.NEGOTIATED, allStatuses[1]);
        assertEquals(Status.IMPLEMENTED, allStatuses[2]);
        assertEquals(Status.DONE, allStatuses[3]);
    }

    @Test
    public void testStatusName() {
        assertEquals("PROPOSED", Status.PROPOSED.name());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.name());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.name());
        assertEquals("DONE", Status.DONE.name());
    }

    @Test
    public void testStatusValueOfInvalidInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            Status.valueOf("INVALID_STATUS");
        });
    }

    @Test
    public void testStatusEquality() {
        assertEquals(Status.PROPOSED, Status.PROPOSED);
        assertNotEquals(Status.PROPOSED, Status.NEGOTIATED);
        assertNotEquals(Status.NEGOTIATED, Status.IMPLEMENTED);
        assertNotEquals(Status.IMPLEMENTED, Status.DONE);
    }

    @Test
    public void testStatusCompareTo() {
        assertTrue(Status.PROPOSED.compareTo(Status.NEGOTIATED) < 0);
        assertTrue(Status.NEGOTIATED.compareTo(Status.IMPLEMENTED) < 0);
        assertTrue(Status.IMPLEMENTED.compareTo(Status.DONE) < 0);
        assertTrue(Status.DONE.compareTo(Status.PROPOSED) > 0);
        assertEquals(0, Status.PROPOSED.compareTo(Status.PROPOSED));
    }
}