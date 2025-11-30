package crm.csv;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVTestTest {

    @Test
    void testConstructor() {
        CSVTest csvTest = new CSVTest();
        assertNotNull(csvTest);
    }

    @Test
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            CSVTest.class.getDeclaredMethod("main", String[].class);
        });
    }

    @Test
    void testMainMethodIsPublic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isPublic(
                CSVTest.class.getDeclaredMethod("main", String[].class).getModifiers()
        ));
    }

    @Test
    void testMainMethodIsStatic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isStatic(
                CSVTest.class.getDeclaredMethod("main", String[].class).getModifiers()
        ));
    }

    @Test
    void testMainMethodReturnTypeIsVoid() throws NoSuchMethodException {
        assertEquals(void.class,
                CSVTest.class.getDeclaredMethod("main", String[].class).getReturnType()
        );
    }
}
