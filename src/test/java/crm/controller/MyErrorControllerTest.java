package crm.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class MyErrorControllerTest {

    private MyErrorController errorController;

    @BeforeEach
    public void setUp() {
        errorController = new MyErrorController();
    }

    @Test
    public void testError() {
        String result = errorController.error();
        assertEquals("Error handling", result);
    }

    @Test
    public void testGetErrorPath() {
        String path = errorController.getErrorPath();
        assertEquals("/error", path);
    }

    @Test
    public void testErrorNotNull() {
        assertNotNull(errorController.error());
    }

    @Test
    public void testErrorPathNotNull() {
        assertNotNull(errorController.getErrorPath());
    }

    @Test
    public void testControllerNotNull() {
        assertNotNull(errorController);
    }
}
