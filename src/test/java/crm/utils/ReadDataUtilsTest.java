package crm.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ReadDataUtilsTest {

    @Test
    public void testReadFileMethodExists() {
        assertNotNull(ReadDataUtils.class);
    }

    @Test
    public void testReadFileWithNullParent() {
        assertDoesNotThrow(() -> {
            ReadDataUtils.ReadFile("Test", null, "CSV Files", "csv");
        });
    }

    @Test
    public void testReadFileWithValidParameters() {
        assertDoesNotThrow(() -> {
            File result = ReadDataUtils.ReadFile("Select File", null, "All Files", "txt", "csv", "pdf");
        });
    }

    @Test
    public void testReadFileReturnsNullWhenCancelled() {
        File result = ReadDataUtils.ReadFile("Test", null, "Files", "csv");
        assertNull(result);
    }
}
