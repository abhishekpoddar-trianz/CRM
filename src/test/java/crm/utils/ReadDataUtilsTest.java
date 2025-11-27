package crm.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ReadDataUtilsTest {

    @Test
    public void testReadDataUtilsConstructor() {
        ReadDataUtils utils = new ReadDataUtils();
        assertNotNull(utils);
    }

    @Test
    public void testReadFileMethodExists() {
        assertDoesNotThrow(() -> {
            // We can't actually test the file chooser dialog without a GUI,
            // but we can ensure the method exists and is callable
            File result = ReadDataUtils.ReadFile("Test", null, "CSV", "csv");
            // Result will be null since we're not in a GUI environment
        });
    }

    @Test
    public void testReadFileWithNullParameters() {
        assertDoesNotThrow(() -> {
            File result = ReadDataUtils.ReadFile(null, null, null, (String[]) null);
        });
    }

    @Test
    public void testReadFileWithEmptyExtension() {
        assertDoesNotThrow(() -> {
            File result = ReadDataUtils.ReadFile("Test", null, "All Files");
        });
    }

    @Test
    public void testReadFileWithMultipleExtensions() {
        assertDoesNotThrow(() -> {
            File result = ReadDataUtils.ReadFile("Test", null, "Documents", "pdf", "doc", "txt");
        });
    }

    @Test
    public void testReadFileMethodSignature() throws NoSuchMethodException {
        assertNotNull(ReadDataUtils.class.getMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class));
    }
}
