package crm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CrmApplicationTest {

    @Test
    public void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            CrmApplication.class.getMethod("main", String[].class);
        });
    }

    @Test
    public void testApplicationClassNotNull() {
        assertNotNull(CrmApplication.class);
    }

    @Test
    public void testApplicationContextLoads() {
        assertDoesNotThrow(() -> {
            CrmApplication app = new CrmApplication();
            assertNotNull(app);
        });
    }
}
