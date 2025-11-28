package crm.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testStatusValues() {
        Status[] values = Status.values();
        assertEquals(4, values.length);
        assertEquals(Status.PROPOSED, values[0]);
        assertEquals(Status.NEGOTIATED, values[1]);
        assertEquals(Status.IMPLEMENTED, values[2]);
        assertEquals(Status.DONE, values[3]);
    }

    @Test
    void testStatusProposed() {
        Status status = Status.PROPOSED;
        assertEquals("PROPOSED", status.name());
    }

    @Test
    void testStatusNegotiated() {
        Status status = Status.NEGOTIATED;
        assertEquals("NEGOTIATED", status.name());
    }

    @Test
    void testStatusImplemented() {
        Status status = Status.IMPLEMENTED;
        assertEquals("IMPLEMENTED", status.name());
    }

    @Test
    void testStatusDone() {
        Status status = Status.DONE;
        assertEquals("DONE", status.name());
    }

    @Test
    void testStatusAllArray() {
        Status[] all = Status.ALL;
        assertEquals(4, all.length);
        assertEquals(Status.PROPOSED, all[0]);
        assertEquals(Status.NEGOTIATED, all[1]);
        assertEquals(Status.IMPLEMENTED, all[2]);
        assertEquals(Status.DONE, all[3]);
    }

    @Test
    void testStatusValueOf() {
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    void testStatusValueOfInvalid() {
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("INVALID"));
    }

    @Test
    void testStatusOrdinal() {
        assertEquals(0, Status.PROPOSED.ordinal());
        assertEquals(1, Status.NEGOTIATED.ordinal());
        assertEquals(2, Status.IMPLEMENTED.ordinal());
        assertEquals(3, Status.DONE.ordinal());
    }

    @Test
    void testStatusComparison() {
        assertTrue(Status.PROPOSED.ordinal() < Status.DONE.ordinal());
        assertTrue(Status.NEGOTIATED.ordinal() < Status.IMPLEMENTED.ordinal());
    }
}
