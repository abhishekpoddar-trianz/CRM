package crm.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

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
    void testReadFileReturnsFileType() throws NoSuchMethodException {
        assertEquals(File.class, ReadDataUtils.class.getMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class).getReturnType());
    }

    @Test
    void testReadFileIsPublic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isPublic(
                ReadDataUtils.class.getMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class).getModifiers()));
    }

    @Test
    void testReadFileIsStatic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isStatic(
                ReadDataUtils.class.getMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class).getModifiers()));
    }

    @Test
    void testConstructor() {
        ReadDataUtils utils = new ReadDataUtils();
        assertNotNull(utils);
    }
}
