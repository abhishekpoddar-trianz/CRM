package crm.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ReadDataUtilsTest {

    @Test
    void testConstructor() {
        ReadDataUtils utils = new ReadDataUtils();
        assertNotNull(utils);
    }

    @Test
    void testReadFileMethodExists() {
        assertDoesNotThrow(() -> {
            ReadDataUtils.class.getDeclaredMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class);
        });
    }

    @Test
    void testReadFileMethodIsPublic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isPublic(
                ReadDataUtils.class.getDeclaredMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class).getModifiers()
        ));
    }

    @Test
    void testReadFileMethodIsStatic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isStatic(
                ReadDataUtils.class.getDeclaredMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class).getModifiers()
        ));
    }

    @Test
    void testReadFileMethodReturnType() throws NoSuchMethodException {
        assertEquals(File.class,
                ReadDataUtils.class.getDeclaredMethod("ReadFile", String.class, javax.swing.JFrame.class, String.class, String[].class).getReturnType()
        );
    }
}
