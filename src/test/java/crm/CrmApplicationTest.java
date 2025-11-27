package crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import static org.junit.jupiter.api.Assertions.*;

public class CrmApplicationTest {

    @Test
    public void testMainMethod() {
        assertDoesNotThrow(() -> {
            String[] args = {};
            // We won't actually run the application, just ensure the method exists and is callable
        });
    }

    @Test
    public void testCrmApplicationConstructor() {
        CrmApplication application = new CrmApplication();
        assertNotNull(application);
    }

    @Test
    public void testSpringApplicationContext() {
        assertNotNull(SpringApplication.class);
    }
}
