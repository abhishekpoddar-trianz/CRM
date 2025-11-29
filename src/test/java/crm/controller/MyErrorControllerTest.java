package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyErrorControllerTest {

    private MyErrorController errorController;

    @BeforeEach
    public void setUp() {
        errorController = new MyErrorController();
    }

    @Test
    public void testErrorMethod() {
        String result = errorController.error();

        assertNotNull(result);
        assertEquals("Error handling", result);
    }

    @Test
    public void testErrorControllerImplementsErrorController() {
        assertTrue(errorController instanceof org.springframework.boot.web.servlet.error.ErrorController);
    }
}
