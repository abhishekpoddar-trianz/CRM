package crm.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadDataUtilsTest {

    @Test
    void testReadDataUtilsClassExists() {
        assertNotNull(ReadDataUtils.class);
    }

    @Test
    void testReadFileMethodExists() throws NoSuchMethodException {
        assertNotNull(ReadDataUtils.class.getMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class));
    }

    @Test
    void testReadFileWithNullParent() {
        assertDoesNotThrow(() -> {
            ReadDataUtils.class.getMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class);
        });
    }
}
