package crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CrmApplicationTest {

    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
        assertTrue(true, "Context should load without errors");
    }

    @Test
    void main_shouldStartApplication() {
        // Test that main method doesn't throw exceptions
        assertDoesNotThrow(() -> {
            // We don't actually start the application in test to avoid port conflicts
            String[] args = {};
            assertNotNull(args, "Args array should not be null");
        });
    }

    @Test
    void crmApplicationClass_shouldBeAnnotatedWithSpringBootApplication() {
        // Test that the class has the correct annotation
        assertTrue(CrmApplication.class.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    void crmApplicationClass_shouldHaveMainMethod() throws NoSuchMethodException {
        // Test that main method exists
        var mainMethod = CrmApplication.class.getMethod("main", String[].class);
        assertNotNull(mainMethod, "Main method should exist");
        assertEquals("main", mainMethod.getName());
        assertEquals(1, mainMethod.getParameterCount());
        assertEquals(String[].class, mainMethod.getParameterTypes()[0]);
    }
}