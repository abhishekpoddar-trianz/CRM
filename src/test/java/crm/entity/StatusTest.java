package crm.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testStatusEnumValues() {
        Status[] values = Status.values();
        assertEquals(4, values.length);
        assertArrayEquals(new Status[]{Status.PROPOSED, Status.NEGOTIATED, Status.IMPLEMENTED, Status.DONE}, values);
    }

    @Test
    void testStatusProposed() {
        Status status = Status.PROPOSED;
        assertNotNull(status);
        assertEquals("PROPOSED", status.name());
        assertEquals(0, status.ordinal());
    }

    @Test
    void testStatusNegotiated() {
        Status status = Status.NEGOTIATED;
        assertNotNull(status);
        assertEquals("NEGOTIATED", status.name());
        assertEquals(1, status.ordinal());
    }

    @Test
    void testStatusImplemented() {
        Status status = Status.IMPLEMENTED;
        assertNotNull(status);
        assertEquals("IMPLEMENTED", status.name());
        assertEquals(2, status.ordinal());
    }

    @Test
    void testStatusDone() {
        Status status = Status.DONE;
        assertNotNull(status);
        assertEquals("DONE", status.name());
        assertEquals(3, status.ordinal());
    }

    @Test
    void testStatusAllConstant() {
        Status[] all = Status.ALL;
        assertNotNull(all);
        assertEquals(4, all.length);
        assertArrayEquals(new Status[]{Status.PROPOSED, Status.NEGOTIATED, Status.IMPLEMENTED, Status.DONE}, all);
    }

    @Test
    void testStatusAllContainsAllValues() {
        Status[] values = Status.values();
        Status[] all = Status.ALL;

        assertEquals(values.length, all.length);
        for (int i = 0; i < values.length; i++) {
            assertEquals(values[i], all[i]);
        }
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
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf("proposed"));
        assertThrows(IllegalArgumentException.class, () -> Status.valueOf(""));
    }

    @Test
    void testStatusValueOfNull() {
        assertThrows(NullPointerException.class, () -> Status.valueOf(null));
    }

    @Test
    void testStatusToString() {
        assertEquals("PROPOSED", Status.PROPOSED.toString());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.toString());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.toString());
        assertEquals("DONE", Status.DONE.toString());
    }

    @Test
    void testStatusEquality() {
        assertEquals(Status.PROPOSED, Status.PROPOSED);
        assertEquals(Status.NEGOTIATED, Status.NEGOTIATED);
        assertEquals(Status.IMPLEMENTED, Status.IMPLEMENTED);
        assertEquals(Status.DONE, Status.DONE);

        assertNotEquals(Status.PROPOSED, Status.NEGOTIATED);
        assertNotEquals(Status.NEGOTIATED, Status.IMPLEMENTED);
        assertNotEquals(Status.IMPLEMENTED, Status.DONE);
    }

    @Test
    void testStatusOrdinals() {
        assertEquals(0, Status.PROPOSED.ordinal());
        assertEquals(1, Status.NEGOTIATED.ordinal());
        assertEquals(2, Status.IMPLEMENTED.ordinal());
        assertEquals(3, Status.DONE.ordinal());
    }

    @Test
    void testEnumIsInstance() {
        assertTrue(Status.PROPOSED instanceof Status);
        assertTrue(Status.NEGOTIATED instanceof Status);
        assertTrue(Status.IMPLEMENTED instanceof Status);
        assertTrue(Status.DONE instanceof Status);
    }
}