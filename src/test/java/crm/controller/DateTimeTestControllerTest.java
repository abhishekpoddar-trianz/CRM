package crm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DateTimeTestControllerTest {

    private DateTimeTestController controller;

    @Mock
    private Model model;

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
        verify(model, times(4)).addAttribute(anyString(), any());
        verify(model).addAttribute(eq("standardDate"), any());
        verify(model).addAttribute(eq("localDateTime"), any());
        verify(model).addAttribute(eq("localDate"), any());
        verify(model).addAttribute(eq("timestamp"), any());
    }

    @Test
    void testDateTimeTestAddsStandardDate() {
        controller.dateTimeTest(model);
        verify(model).addAttribute(eq("standardDate"), any(java.util.Date.class));
    }

    @Test
    void testDateTimeTestAddsLocalDateTime() {
        controller.dateTimeTest(model);
        verify(model).addAttribute(eq("localDateTime"), any(java.time.LocalDateTime.class));
    }

    @Test
    void testDateTimeTestAddsLocalDate() {
        controller.dateTimeTest(model);
        verify(model).addAttribute(eq("localDate"), any(java.time.LocalDate.class));
    }

    @Test
    void testDateTimeTestAddsTimestamp() {
        controller.dateTimeTest(model);
        verify(model).addAttribute(eq("timestamp"), any(java.time.Instant.class));
    }

    @Test
    void testDateTimeTestReturnsCorrectView() {
        String viewName = controller.dateTimeTest(model);
        assertNotNull(viewName);
        assertFalse(viewName.isEmpty());
        assertEquals("date/test", viewName);
    }
}
