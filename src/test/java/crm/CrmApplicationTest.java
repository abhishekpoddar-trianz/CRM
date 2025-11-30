package crm;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrmApplicationTest {

    @Test
    void testConstructor() {
        CrmApplication app = new CrmApplication();
        assertNotNull(app);
    }

    @Test
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            CrmApplication.class.getDeclaredMethod("main", String[].class);
        });
    }

    @Test
    void testMainMethodIsPublic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isPublic(
                CrmApplication.class.getDeclaredMethod("main", String[].class).getModifiers()
        ));
    }

    @Test
    void testMainMethodIsStatic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isStatic(
                CrmApplication.class.getDeclaredMethod("main", String[].class).getModifiers()
        ));
    }

    @Test
    void testMainMethodReturnTypeIsVoid() throws NoSuchMethodException {
        assertEquals(void.class,
                CrmApplication.class.getDeclaredMethod("main", String[].class).getReturnType()
        );
    }

    @Test
    void testClassHasSpringBootApplicationAnnotation() {
        assertTrue(CrmApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }
}
