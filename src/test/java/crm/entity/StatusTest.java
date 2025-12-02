package crm.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    @Test
    public void testStatusEnumValues() {
        assertEquals(4, Status.values().length);
    }

    @Test
    public void testStatusProposed() {
        assertEquals(Status.PROPOSED, Status.valueOf("PROPOSED"));
    }

    @Test
    public void testStatusNegotiated() {
        assertEquals(Status.NEGOTIATED, Status.valueOf("NEGOTIATED"));
    }

    @Test
    public void testStatusImplemented() {
        assertEquals(Status.IMPLEMENTED, Status.valueOf("IMPLEMENTED"));
    }

    @Test
    public void testStatusDone() {
        assertEquals(Status.DONE, Status.valueOf("DONE"));
    }

    @Test
    public void testAllConstant() {
        assertNotNull(Status.ALL);
        assertEquals(4, Status.ALL.length);
        assertArrayEquals(new Status[]{Status.PROPOSED, Status.NEGOTIATED, Status.IMPLEMENTED, Status.DONE}, Status.ALL);
    }

    @Test
    public void testAllContainsAllValues() {
        for (Status status : Status.values()) {
            boolean found = false;
            for (Status s : Status.ALL) {
                if (s == status) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
    }
}
