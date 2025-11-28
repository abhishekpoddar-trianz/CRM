package crm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrmApplicationTest {

    @Test
    void contextLoads() {
        assertDoesNotThrow(() -> {
            CrmApplication.main(new String[]{});
        });
    }

    @Test
    void testApplicationContext() {
        assertNotNull(CrmApplication.class);
    }
}
