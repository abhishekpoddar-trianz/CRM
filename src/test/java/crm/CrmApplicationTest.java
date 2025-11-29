package crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.*;

public class CrmApplicationTest {

    @Test
    public void testMainMethod() {
        assertDoesNotThrow(() -> {
            String[] args = {};
        });
    }

    @Test
    public void testApplicationContext() {
        assertNotNull(CrmApplication.class);
    }

    @Test
    public void testSpringBootApplicationAnnotation() {
        assertTrue(CrmApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }
}
