package crm.csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVTestTest {

    @Test
    void testCSVTestClassExists() {
        assertNotNull(CSVTest.class);
    }

    @Test
    void testMainMethodExists() throws NoSuchMethodException {
        assertNotNull(CSVTest.class.getMethod("main", String[].class));
    }
}
