package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyErrorControllerTest {

    private MyErrorController myErrorController;

    @BeforeEach
    void setUp() {
        myErrorController = new MyErrorController();
    }

    @Test
    void testConstructor() {
        MyErrorController controller = new MyErrorController();
        assertNotNull(controller);
    }

    @Test
    void testError() {
        String result = myErrorController.error();

        assertNotNull(result);
        assertEquals("Error handling", result);
    }

    @Test
    void testGetErrorPath() {
        String errorPath = myErrorController.getErrorPath();

        assertNotNull(errorPath);
        assertEquals("/error", errorPath);
    }

    @Test
    void testErrorReturnsCorrectMessage() {
        String message = myErrorController.error();

        assertEquals("Error handling", message);
    }

    @Test
    void testGetErrorPathReturnsCorrectPath() {
        String path = myErrorController.getErrorPath();

        assertEquals("/error", path);
    }

    @Test
    void testErrorMethodIsNotNull() {
        String result = myErrorController.error();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetErrorPathIsNotNull() {
        String path = myErrorController.getErrorPath();

        assertNotNull(path);
        assertFalse(path.isEmpty());
    }
}
