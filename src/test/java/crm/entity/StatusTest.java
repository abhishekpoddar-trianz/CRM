package crm.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testStatusEnumValues() {
        assertEquals(4, Status.values().length);
    }

    @Test
    void testProposedStatus() {
        Status status = Status.PROPOSED;
        assertNotNull(status);
        assertEquals("PROPOSED", status.name());
    }

    @Test
    void testNegotiatedStatus() {
        Status status = Status.NEGOTIATED;
        assertNotNull(status);
        assertEquals("NEGOTIATED", status.name());
    }

    @Test
    void testImplementedStatus() {
        Status status = Status.IMPLEMENTED;
        assertNotNull(status);
        assertEquals("IMPLEMENTED", status.name());
    }

    @Test
    void testDoneStatus() {
        Status status = Status.DONE;
        assertNotNull(status);
        assertEquals("DONE", status.name());
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
        assertThrows(IllegalArgumentException.class, () -> {
            Status.valueOf("INVALID");
        });
    }

    @Test
    void testStatusAllArray() {
        assertNotNull(Status.ALL);
        assertEquals(4, Status.ALL.length);
    }

    @Test
    void testStatusAllContainsProposed() {
        boolean found = false;
        for (Status status : Status.ALL) {
            if (status == Status.PROPOSED) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testStatusAllContainsNegotiated() {
        boolean found = false;
        for (Status status : Status.ALL) {
            if (status == Status.NEGOTIATED) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testStatusAllContainsImplemented() {
        boolean found = false;
        for (Status status : Status.ALL) {
            if (status == Status.IMPLEMENTED) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @Test
    void testStatusAllContainsDone() {
        boolean found = false;
        for (Status status : Status.ALL) {
            if (status == Status.DONE) {
                found = true;
                break;
            }
        }
        assertTrue(found);
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
        assertTrue(Status.PROPOSED.ordinal() < Status.NEGOTIATED.ordinal());
        assertTrue(Status.NEGOTIATED.ordinal() < Status.IMPLEMENTED.ordinal());
        assertTrue(Status.IMPLEMENTED.ordinal() < Status.DONE.ordinal());
    }

    @Test
    void testStatusEquality() {
        assertEquals(Status.PROPOSED, Status.PROPOSED);
        assertEquals(Status.NEGOTIATED, Status.NEGOTIATED);
        assertNotEquals(Status.PROPOSED, Status.DONE);
    }
}
