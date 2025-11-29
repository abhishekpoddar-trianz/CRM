package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class MyErrorControllerTest {

    private MyErrorController myErrorController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        myErrorController = new MyErrorController();
        mockMvc = MockMvcBuilders.standaloneSetup(myErrorController).build();
    }

    @Test
    void testConstructor() {
        assertNotNull(myErrorController);
    }

    @Test
    void testError() {
        String result = myErrorController.error();
        assertEquals("Error handling", result);
        assertNotNull(result);
    }

    @Test
    void testGetErrorPath() {
        String result = myErrorController.getErrorPath();
        assertEquals("/error", result);
        assertNotNull(result);
    }

    @Test
    void testErrorPathConstant() throws Exception {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk())
                .andExpect(content().string("Error handling"));
    }

    @Test
    void testErrorMethodReturnsNonEmptyString() {
        String result = myErrorController.error();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertTrue(result.contains("Error"));
    }

    @Test
    void testGetErrorPathReturnsValidPath() {
        String result = myErrorController.getErrorPath();
        assertNotNull(result);
        assertTrue(result.startsWith("/"));
        assertEquals("/error", result);
    }

    @Test
    void testMultipleCallsReturnSameValue() {
        String result1 = myErrorController.error();
        String result2 = myErrorController.error();
        assertEquals(result1, result2);

        String path1 = myErrorController.getErrorPath();
        String path2 = myErrorController.getErrorPath();
        assertEquals(path1, path2);
    }
}