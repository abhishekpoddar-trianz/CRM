package crm.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    @Test
    public void testStatusEnumValues() {
        Status[] statuses = Status.values();
        assertEquals(4, statuses.length);
        assertEquals(Status.PROPOSED, statuses[0]);
        assertEquals(Status.NEGOTIATED, statuses[1]);
        assertEquals(Status.IMPLEMENTED, statuses[2]);
        assertEquals(Status.DONE, statuses[3]);
    }

    @Test
    public void testStatusValueOf() {
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    public void testStatusAllField() {
        assertNotNull(Status.ALL);
        assertEquals(4, Status.ALL.length);
        assertEquals(Status.PROPOSED, Status.ALL[0]);
        assertEquals(Status.NEGOTIATED, Status.ALL[1]);
        assertEquals(Status.IMPLEMENTED, Status.ALL[2]);
        assertEquals(Status.DONE, Status.ALL[3]);
    }

    @Test
    public void testInvalidStatusValueOf() {
        assertThrows(IllegalArgumentException.class, () -> {
            Status.valueOf("INVALID");
        });
    }

    @Test
    public void testStatusName() {
        assertEquals("PROPOSED", Status.PROPOSED.name());
        assertEquals("NEGOTIATED", Status.NEGOTIATED.name());
        assertEquals("IMPLEMENTED", Status.IMPLEMENTED.name());
        assertEquals("DONE", Status.DONE.name());
    }
}
