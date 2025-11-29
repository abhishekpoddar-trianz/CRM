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
import static org.mockito.Mockito.*;

class DateTimeTestControllerTest {

    @Mock
    private Model model;

    private DateTimeTestController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new DateTimeTestController();
    }

    @Test
    void testConstructor() {
        assertNotNull(controller);
    }

    @Test
    void testDateTimeTest() {
        String result = controller.dateTimeTest(model);

        assertEquals("date/test", result);
        verify(model, times(4)).addAttribute(anyString(), any())();
    }

    @Test
    void testDateTimeTestAddsAllAttributes() {
        controller.dateTimeTest(model);

        verify(model).addAttribute(eq("standardDate"), any(Date.class));
        verify(model).addAttribute(eq("localDateTime"), any(LocalDateTime.class));
        verify(model).addAttribute(eq("localDate"), any(LocalDate.class));
        verify(model).addAttribute(eq("timestamp"), any(Instant.class));
    }

    @Test
    void testDateTimeTestAddsStandardDate() {
        controller.dateTimeTest(model);

        verify(model).addAttribute(eq("standardDate"), any(Date.class));
    }

    @Test
    void testDateTimeTestAddsLocalDateTime() {
        controller.dateTimeTest(model);

        verify(model).addAttribute(eq("localDateTime"), any(LocalDateTime.class));
    }

    @Test
    void testDateTimeTestAddsLocalDate() {
        controller.dateTimeTest(model);

        verify(model).addAttribute(eq("localDate"), any(LocalDate.class));
    }

    @Test
    void testDateTimeTestAddsTimestamp() {
        controller.dateTimeTest(model);

        verify(model).addAttribute(eq("timestamp"), any(Instant.class));
    }

    @Test
    void testDateTimeTestReturnsCorrectViewName() {
        String result = controller.dateTimeTest(model);

        assertNotNull(result);
        assertEquals("date/test", result);
        assertTrue(result.contains("date"));
        assertTrue(result.contains("test"));
    }

    @Test
    void testDateTimeTestWithNullModel() {
        assertDoesNotThrow(() -> controller.dateTimeTest(null));
    }

    @Test
    void testMultipleCallsReturnSameViewName() {
        String result1 = controller.dateTimeTest(model);
        String result2 = controller.dateTimeTest(model);

        assertEquals(result1, result2);
        assertEquals("date/test", result1);
        assertEquals("date/test", result2);
    }

    @Test
    void testDateTimeTestAddsNonNullValues() {
        controller.dateTimeTest(model);

        verify(model).addAttribute(eq("standardDate"), notNull());
        verify(model).addAttribute(eq("localDateTime"), notNull());
        verify(model).addAttribute(eq("localDate"), notNull());
        verify(model).addAttribute(eq("timestamp"), notNull());
    }
}