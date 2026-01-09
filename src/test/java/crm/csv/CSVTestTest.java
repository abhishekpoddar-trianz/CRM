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

    @Test
    void testMainMethodIsPublic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isPublic(
                CSVTest.class.getMethod("main", String[].class).getModifiers()));
    }

    @Test
    void testMainMethodIsStatic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isStatic(
                CSVTest.class.getMethod("main", String[].class).getModifiers()));
    }

    @Test
    void testMainMethodReturnsVoid() throws NoSuchMethodException {
        assertEquals(void.class, CSVTest.class.getMethod("main", String[].class).getReturnType());
    }
}
