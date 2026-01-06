package crm.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void testStatusEnumValues() {
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    void testAllStatusArray() {
        Status[] allStatuses = Status.ALL;
        assertNotNull(allStatuses);
        assertEquals(4, allStatuses.length);
        assertEquals(Status.PROPOSED, allStatuses[0]);
        assertEquals(Status.NEGOTIATED, allStatuses[1]);
        assertEquals(Status.IMPLEMENTED, allStatuses[2]);
        assertEquals(Status.DONE, allStatuses[3]);
    }

    @Test
    void testStatusComparison() {
        Status status1 = Status.PROPOSED;
        Status status2 = Status.PROPOSED;
        Status status3 = Status.DONE;

        assertEquals(status1, status2);
        assertNotEquals(status1, status3);
    }

    @Test
    void testStatusToString() {
        assertEquals("PROPOSED", Status.PROPOSED.toString());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.toString());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.toString());
        assertEquals("DONE", Status.DONE.toString());
    }

    @Test
    void testStatusValuesMethod() {
        Status[] values = Status.values();
        assertEquals(4, values.length);
        assertTrue(containsStatus(values, Status.PROPOSED));
        assertTrue(containsStatus(values, Status.NEGOTIATED));
        assertTrue(containsStatus(values, Status.IMPLEMENTED));
        assertTrue(containsStatus(values, Status.DONE));
    }

    private boolean containsStatus(Status[] statuses, Status target) {
        for (Status status : statuses) {
            if (status == target) {
                return true;
            }
        }
        return false;
    }
}
