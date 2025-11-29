package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class DateTimeTestControllerTest {

    @Mock
    private Model model;

    private DateTimeTestController dateTimeTestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dateTimeTestController = new DateTimeTestController();
    }

    @Test
    void testConstructor() {
        assertNotNull(dateTimeTestController);
    }

    @Test
    void testDateTimeTest() {
        String result = dateTimeTestController.dateTimeTest(model);

        assertEquals("date/test", result);

        // Verify that all date/time attributes are added to the model
        verify(model).addAttribute(eq("standardDate"), any(Date.class));
        verify(model).addAttribute(eq("localDateTime"), any(LocalDateTime.class));
        verify(model).addAttribute(eq("localDate"), any(LocalDate.class));
        verify(model).addAttribute(eq("timestamp"), any(Instant.class));
    }

    @Test
    void testDateTimeTest_ModelNotNull() {
        String result = dateTimeTestController.dateTimeTest(model);

        assertEquals("date/test", result);

        // Verify model interactions occurred
        verify(model, times(4)).addAttribute(anyString(), any());
    }

    @Test
    void testDateTimeTest_AttributeTypes() {
        dateTimeTestController.dateTimeTest(model);

        // Verify specific types are added
        verify(model).addAttribute(eq("standardDate"), any(Date.class));
        verify(model).addAttribute(eq("localDateTime"), any(LocalDateTime.class));
        verify(model).addAttribute(eq("localDate"), any(LocalDate.class));
        verify(model).addAttribute(eq("timestamp"), any(Instant.class));
    }

    @Test
    void testDateTimeTest_ReturnValue() {
        String result = dateTimeTestController.dateTimeTest(model);

        assertNotNull(result);
        assertEquals("date/test", result);
    }

    @Test
    void testDateTimeTest_NoExceptions() {
        assertDoesNotThrow(() -> {
            dateTimeTestController.dateTimeTest(model);
        });
    }
}