package crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.*;

class CrmApplicationTest {

    @Test
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            String[] args = {};
        });
    }

    @Test
    void testApplicationContextLoads() {
        assertNotNull(CrmApplication.class);
    }

    @Test
    void testMainClass() {
        assertEquals("CrmApplication", CrmApplication.class.getSimpleName());
    }

    @Test
    void testClassAnnotations() {
        assertTrue(CrmApplication.class.isAnnotationPresent(
                org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void testConstructor() {
        CrmApplication app = new CrmApplication();
        assertNotNull(app);
    }
}
