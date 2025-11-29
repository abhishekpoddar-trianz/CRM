package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.ErrorController;

import static org.junit.jupiter.api.Assertions.*;

class MyErrorControllerTest {

    private MyErrorController myErrorController;

    @BeforeEach
    void setUp() {
        myErrorController = new MyErrorController();
    }

    @Test
    void testConstructor() {
        assertNotNull(myErrorController);
    }

    @Test
    void testInstanceOfErrorController() {
        assertTrue(myErrorController instanceof ErrorController);
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
    void testErrorReturnsExpectedString() {
        String result = myErrorController.error();
        assertEquals("Error handling", result);
    }

    @Test
    void testGetErrorPathReturnsExpectedPath() {
        String path = myErrorController.getErrorPath();
        assertEquals("/error", path);
    }

    @Test
    void testErrorMethodNotNull() {
        String result = myErrorController.error();
        assertNotNull(result);
    }

    @Test
    void testGetErrorPathMethodNotNull() {
        String path = myErrorController.getErrorPath();
        assertNotNull(path);
    }

    @Test
    void testErrorPathConstant() {
        // Test that the error path is consistent
        String path1 = myErrorController.getErrorPath();
        String path2 = myErrorController.getErrorPath();
        assertEquals(path1, path2);
    }

    @Test
    void testErrorMessageConstant() {
        // Test that the error message is consistent
        String message1 = myErrorController.error();
        String message2 = myErrorController.error();
        assertEquals(message1, message2);
    }

    @Test
    void testNoExceptionsThrown() {
        assertDoesNotThrow(() -> {
            myErrorController.error();
            myErrorController.getErrorPath();
        });
    }
}