package crm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrmApplicationTest {

    @Test
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            CrmApplication.class.getDeclaredMethod("main", String[].class);
        });
    }

    @Test
    void testMainMethodIsStatic() throws Exception {
        var method = CrmApplication.class.getDeclaredMethod("main", String[].class);
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()));
        assertTrue(java.lang.reflect.Modifier.isPublic(method.getModifiers()));
    }

    @Test
    void testMainMethodReturnsVoid() throws Exception {
        var method = CrmApplication.class.getDeclaredMethod("main", String[].class);
        assertEquals(void.class, method.getReturnType());
    }

    @Test
    void testCrmApplicationExists() {
        assertNotNull(CrmApplication.class);
    }

    @Test
    void testCrmApplicationAnnotations() {
        assertTrue(CrmApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }
}