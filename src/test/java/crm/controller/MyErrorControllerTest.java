package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyErrorControllerTest {

    private MyErrorController errorController;

    @BeforeEach
    void setUp() {
        errorController = new MyErrorController();
    }

    @Test
    void testConstructor() {
        assertNotNull(errorController);
    }

    @Test
    void testError() {
        String result = errorController.error();
        assertEquals("Error handling", result);
    }

    @Test
    void testErrorReturnsNonNullValue() {
        String result = errorController.error();
        assertNotNull(result);
    }

    @Test
    void testErrorReturnsNonEmptyString() {
        String result = errorController.error();
        assertFalse(result.isEmpty());
    }

    @Test
    void testErrorReturnsExpectedMessage() {
        String result = errorController.error();
        assertEquals("Error handling", result);
        assertTrue(result.contains("Error"));
        assertTrue(result.contains("handling"));
    }

    @Test
    void testImplementsErrorController() {
        assertTrue(errorController instanceof org.springframework.boot.web.servlet.error.ErrorController);
    }
}
